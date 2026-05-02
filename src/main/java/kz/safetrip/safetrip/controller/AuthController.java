package kz.safetrip.safetrip.controller;

import jakarta.validation.Valid;
import kz.safetrip.safetrip.model.dto.auth.AuthResponse;
import kz.safetrip.safetrip.model.dto.auth.ForgotPasswordRequest;
import kz.safetrip.safetrip.model.dto.auth.LoginRequest;
import kz.safetrip.safetrip.model.dto.auth.RegisterRequest;
import kz.safetrip.safetrip.model.dto.auth.ResetPasswordRequest;
import kz.safetrip.safetrip.model.dto.auth.VerifyPasswordResetOtpRequest;
import kz.safetrip.safetrip.model.dto.auth.VerifyPasswordResetOtpResponse;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindStartResponse;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindStatusResponse;
import kz.safetrip.safetrip.service.AuthService;
import kz.safetrip.safetrip.service.telegram.TelegramLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TelegramLinkService telegramLinkService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/telegram-link/start")
    public ResponseEntity<TelegramBindStartResponse> startTelegramBinding() {
        return ResponseEntity.ok(telegramLinkService.startTelegramBinding());
    }

    @GetMapping("/telegram-link/status")
    public ResponseEntity<TelegramBindStatusResponse> getTelegramBindingStatus() {
        return ResponseEntity.ok(telegramLinkService.getTelegramBindingStatus());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password/verify-otp")
    public ResponseEntity<VerifyPasswordResetOtpResponse> verifyPasswordResetOtp(
            @Valid @RequestBody VerifyPasswordResetOtpRequest request
    ) {
        return ResponseEntity.ok(authService.verifyPasswordResetOtp(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok().build();
    }
}
