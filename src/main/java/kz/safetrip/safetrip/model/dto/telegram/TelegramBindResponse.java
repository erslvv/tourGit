package kz.safetrip.safetrip.model.dto.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TelegramBindResponse {
    private String message;
    private String email;
}
