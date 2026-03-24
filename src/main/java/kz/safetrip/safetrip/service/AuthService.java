package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.model.dto.auth.AuthResponse;
import kz.safetrip.safetrip.model.dto.auth.LoginRequest;
import kz.safetrip.safetrip.model.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
