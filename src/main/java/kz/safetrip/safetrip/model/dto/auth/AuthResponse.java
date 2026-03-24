package kz.safetrip.safetrip.model.dto.auth;

import kz.safetrip.safetrip.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private UserDto user;
}
