package kz.safetrip.safetrip.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerifyPasswordResetOtpResponse {
    private String resetToken;
}
