package kz.safetrip.safetrip.service.impl.telegram;

import kz.safetrip.safetrip.model.dto.telegram.TelegramBindRequest;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindResponse;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindStartResponse;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindStatusResponse;
import kz.safetrip.safetrip.model.entity.TelegramBindToken;
import kz.safetrip.safetrip.model.entity.User;
import kz.safetrip.safetrip.repository.UserRepository;
import kz.safetrip.safetrip.repository.jpa.TelegramBindTokenJpaRepository;
import kz.safetrip.safetrip.security.CurrentUserService;
import kz.safetrip.safetrip.service.telegram.TelegramLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TelegramLinkServiceImpl implements TelegramLinkService {

    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final TelegramBindTokenJpaRepository telegramBindTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.telegram.bot-username:}")
    private String botUsername;

    @Value("${app.telegram.bind.expiration-minutes:15}")
    private long bindExpirationMinutes;

    @Override
    @Transactional
    public TelegramBindStartResponse startTelegramBinding() {
        User user = currentUserService.getCurrentUser();

        if (botUsername == null || botUsername.isBlank()) {
            throw new IllegalStateException("Telegram bot username is not configured");
        }

        if (Boolean.TRUE.equals(user.getTelegramVerified())) {
            return new TelegramBindStartResponse(
                    botUsername,
                    null,
                    "https://t.me/" + botUsername,
                    null,
                    true
            );
        }

        LocalDateTime now = LocalDateTime.now();
        telegramBindTokenRepository.markActiveTokensAsUsed(user.getId(), now);

        String rawCode = generateRawCode();
        TelegramBindToken token = new TelegramBindToken();
        token.setUser(user);
        token.setCodeHash(hash(rawCode));
        token.setExpiresAt(now.plusMinutes(bindExpirationMinutes));
        telegramBindTokenRepository.save(token);

        String startUrl = "https://t.me/" + botUsername + "?start=bind_" + rawCode;
        return new TelegramBindStartResponse(botUsername, rawCode, startUrl, token.getExpiresAt(), false);
    }

    @Override
    public TelegramBindStatusResponse getTelegramBindingStatus() {
        User user = currentUserService.getCurrentUser();
        return new TelegramBindStatusResponse(
                Boolean.TRUE.equals(user.getTelegramVerified()),
                user.getTelegramUsername()
        );
    }

    @Override
    @Transactional
    public TelegramBindResponse bindTelegramAccount(TelegramBindRequest request) {
        LocalDateTime now = LocalDateTime.now();
        String codeHash = hash(request.getBindCode().trim());

        TelegramBindToken token = telegramBindTokenRepository.findByCodeHash(codeHash)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Telegram bind code"));

        if (token.getUsedAt() != null) {
            throw new IllegalArgumentException("Telegram bind code already used");
        }

        if (token.getExpiresAt().isBefore(now)) {
            throw new IllegalArgumentException("Telegram bind code expired");
        }

        String chatId = request.getChatId().trim();
        User user = token.getUser();

        userRepository.findByTelegramChatId(chatId).ifPresent(existing -> {
            if (!existing.getId().equals(user.getId())) {
                throw new IllegalArgumentException("This Telegram account is already linked to another user");
            }
        });

        user.setTelegramChatId(chatId);
        user.setTelegramUsername(normalizeUsername(request.getUsername()));
        user.setTelegramVerified(true);
        userRepository.save(user);

        token.setUsedAt(now);
        telegramBindTokenRepository.save(token);

        return new TelegramBindResponse("Telegram account linked successfully", user.getEmail());
    }

    private String normalizeUsername(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }
        String trimmed = username.trim();
        return trimmed.startsWith("@") ? trimmed.substring(1) : trimmed;
    }

    private String generateRawCode() {
        byte[] bytes = new byte[24];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
}
