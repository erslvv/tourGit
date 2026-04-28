package kz.safetrip.safetrip.service.impl;

import kz.safetrip.safetrip.enumeration.UserRole;
import kz.safetrip.safetrip.mapper.UserMapper;
import kz.safetrip.safetrip.model.dto.auth.AuthResponse;
import kz.safetrip.safetrip.model.dto.auth.LoginRequest;
import kz.safetrip.safetrip.model.dto.auth.RegisterRequest;
import kz.safetrip.safetrip.model.entity.User;
import kz.safetrip.safetrip.repository.UserRepository;
import kz.safetrip.safetrip.security.JwtService;
import kz.safetrip.safetrip.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) throw new IllegalArgumentException("User with email already exists: " + email);
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setIsActive(true);
        User saved = userRepository.save(user);
        return AuthResponse.builder().accessToken(jwtService.generateToken(saved)).tokenType("Bearer").user(userMapper.toDto(saved)).build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
        return AuthResponse.builder().accessToken(jwtService.generateToken(user)).tokenType("Bearer").user(userMapper.toDto(user)).build();
    }
}
