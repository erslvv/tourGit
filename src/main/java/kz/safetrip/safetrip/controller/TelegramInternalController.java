package kz.safetrip.safetrip.controller;

import jakarta.validation.Valid;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindRequest;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindResponse;
import kz.safetrip.safetrip.service.telegram.TelegramLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/internal/telegram")
@RequiredArgsConstructor
public class TelegramInternalController {

    private final TelegramLinkService telegramLinkService;

    @Value("${app.telegram.bot-secret:}")
    private String botSecret;

    @PostMapping("/bind")
    public ResponseEntity<TelegramBindResponse> bindTelegramAccount(
            @RequestHeader(value = "X-Bot-Secret", required = false) String secret,
            @Valid @RequestBody TelegramBindRequest request
    ) {
        if (botSecret == null || botSecret.isBlank() || secret == null || !botSecret.equals(secret)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid bot secret");
        }

        return ResponseEntity.ok(telegramLinkService.bindTelegramAccount(request));
    }
}
