package kz.safetrip.safetrip.model.dto;

import kz.safetrip.safetrip.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private UserRole role;
    private Boolean isActive;
    private String telegramUsername;
    private Boolean telegramVerified;
    private LocalDateTime createdAt;
}
