package kz.safetrip.safetrip.service.impl;

import kz.safetrip.safetrip.enumeration.UserRole;
import kz.safetrip.safetrip.mapper.UserMapper;
import kz.safetrip.safetrip.model.dto.auth.AuthResponse;
import kz.safetrip.safetrip.model.dto.auth.ForgotPasswordRequest;
import kz.safetrip.safetrip.model.dto.auth.LoginRequest;
import kz.safetrip.safetrip.model.dto.auth.RegisterRequest;
import kz.safetrip.safetrip.model.dto.auth.ResetPasswordRequest;
import kz.safetrip.safetrip.model.dto.auth.VerifyPasswordResetOtpRequest;
import kz.safetrip.safetrip.model.dto.auth.VerifyPasswordResetOtpResponse;
import kz.safetrip.safetrip.model.entity.PasswordResetOtp;
import kz.safetrip.safetrip.model.entity.PasswordResetToken;
import kz.safetrip.safetrip.model.entity.User;
import kz.safetrip.safetrip.repository.UserRepository;
import kz.safetrip.safetrip.repository.jpa.PasswordResetOtpJpaRepository;
import kz.safetrip.safetrip.repository.jpa.PasswordResetTokenJpaRepository;
import kz.safetrip.safetrip.security.JwtService;
import kz.safetrip.safetrip.service.AuthService;
import kz.safetrip.safetrip.service.telegram.TelegramBotClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final PasswordResetTokenJpaRepository passwordResetTokenRepository;
    private final PasswordResetOtpJpaRepository passwordResetOtpRepository;
    private final TelegramBotClient telegramBotClient;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.password-reset.expiration-minutes:15}")
    private long resetTokenExpirationMinutes;

    @Value("${app.password-reset.otp.expiration-minutes:5}")
    private long otpExpirationMinutes;

    @Value("${app.password-reset.otp.max-attempts:5}")
    private int otpMaxAttempts;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.getEmail());
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email already exists: " + email);
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setIsActive(true);
        user.setTelegramVerified(false);

        User saved = userRepository.save(user);
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(saved))
                .tokenType("Bearer")
                .user(userMapper.toDto(saved))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        String email = normalizeEmail(request.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .tokenType("Bearer")
                .user(userMapper.toDto(user))
                .build();
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        String email = normalizeEmail(request.getEmail());

        userRepository.findByEmail(email).ifPresent(user -> {
            if (!Boolean.TRUE.equals(user.getTelegramVerified()) || user.getTelegramChatId() == null || user.getTelegramChatId().isBlank()) {
                log.warn("Password reset requested for {}, but Telegram is not linked", email);
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            passwordResetOtpRepository.markActiveOtpsAsUsed(user.getId(), now);

            String rawOtp = generateOtp();
            PasswordResetOtp otp = new PasswordResetOtp();
            otp.setUser(user);
            otp.setOtpHash(hashToken(rawOtp));
            otp.setExpiresAt(now.plusMinutes(otpExpirationMinutes));
            otp.setAttempts(0);
            passwordResetOtpRepository.save(otp);

            String text = "SafeTrip password reset code: " + rawOtp
                    + "\n\nThis code expires in " + otpExpirationMinutes + " minutes."
                    + "\nIf you did not request it, ignore this message.";

            telegramBotClient.sendMessage(user.getTelegramChatId(), text);
            log.info("Password reset OTP sent to Telegram for user {}", email);
        });
    }

    @Override
    @Transactional
    public VerifyPasswordResetOtpResponse verifyPasswordResetOtp(VerifyPasswordResetOtpRequest request) {
        String email = normalizeEmail(request.getEmail());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired OTP"));

        PasswordResetOtp otp = passwordResetOtpRepository.findTopByUser_IdAndUsedAtIsNullOrderByCreatedAtDesc(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired OTP"));

        LocalDateTime now = LocalDateTime.now();
        if (otp.getExpiresAt().isBefore(now)) {
            otp.setUsedAt(now);
            passwordResetOtpRepository.save(otp);
            throw new IllegalArgumentException("OTP expired");
        }

        if (otp.getAttempts() >= otpMaxAttempts) {
            otp.setUsedAt(now);
            passwordResetOtpRepository.save(otp);
            throw new IllegalArgumentException("Too many OTP attempts");
        }

        if (!hashToken(request.getOtp().trim()).equals(otp.getOtpHash())) {
            otp.setAttempts(otp.getAttempts() + 1);
            passwordResetOtpRepository.save(otp);
            throw new IllegalArgumentException("Invalid or expired OTP");
        }

        otp.setUsedAt(now);
        passwordResetOtpRepository.save(otp);

        passwordResetTokenRepository.markActiveTokensAsUsed(user.getId(), now);
        String rawToken = generateRawToken();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setTokenHash(hashToken(rawToken));
        resetToken.setExpiresAt(now.plusMinutes(resetTokenExpirationMinutes));
        passwordResetTokenRepository.save(resetToken);

        return new VerifyPasswordResetOtpResponse(rawToken);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        LocalDateTime now = LocalDateTime.now();
        String tokenHash = hashToken(request.getToken());

        PasswordResetToken token = passwordResetTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired reset token"));

        if (token.getUsedAt() != null) {
            throw new IllegalArgumentException("Reset token already used");
        }

        if (token.getExpiresAt().isBefore(now)) {
            throw new IllegalArgumentException("Reset token expired");
        }

        User user = token.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        token.setUsedAt(now);
        passwordResetTokenRepository.save(token);
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private String generateOtp() {
        return String.format("%06d", secureRandom.nextInt(1_000_000));
    }

    private String generateRawToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
}
