package kz.safetrip.safetrip.model.dto.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TelegramBindStatusResponse {
    private Boolean telegramVerified;
    private String telegramUsername;
}
