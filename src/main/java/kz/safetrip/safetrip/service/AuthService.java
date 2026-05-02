package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.model.dto.auth.AuthResponse;
import kz.safetrip.safetrip.model.dto.auth.ForgotPasswordRequest;
import kz.safetrip.safetrip.model.dto.auth.LoginRequest;
import kz.safetrip.safetrip.model.dto.auth.RegisterRequest;
import kz.safetrip.safetrip.model.dto.auth.ResetPasswordRequest;
import kz.safetrip.safetrip.model.dto.auth.VerifyPasswordResetOtpRequest;
import kz.safetrip.safetrip.model.dto.auth.VerifyPasswordResetOtpResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    VerifyPasswordResetOtpResponse verifyPasswordResetOtp(VerifyPasswordResetOtpRequest request);
    void resetPassword(ResetPasswordRequest request);
}
