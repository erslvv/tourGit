package kz.safetrip.safetrip.model.dto.telegram;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramBindRequest {
    @NotBlank
    private String bindCode;

    @NotBlank
    private String chatId;

    private String username;
    private String firstName;
    private String lastName;
}
