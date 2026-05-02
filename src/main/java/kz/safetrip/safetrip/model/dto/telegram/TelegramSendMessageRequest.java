package kz.safetrip.safetrip.model.dto.telegram;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramSendMessageRequest {

    private String chatId;

    private String text;
}