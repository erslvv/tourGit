package kz.safetrip.safetrip.model.dto.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TelegramBindStartResponse {
    private String botUsername;
    private String bindCode;
    private String telegramStartUrl;
    private LocalDateTime expiresAt;
    private Boolean telegramVerified;
}
