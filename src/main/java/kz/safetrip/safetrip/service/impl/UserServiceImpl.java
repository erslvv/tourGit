package kz.safetrip.safetrip.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.safetrip.safetrip.enumeration.UserRole;
import kz.safetrip.safetrip.mapper.UserMapper;
import kz.safetrip.safetrip.model.dto.UserDto;
import kz.safetrip.safetrip.model.entity.User;
import kz.safetrip.safetrip.repository.UserRepository;
import kz.safetrip.safetrip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto create(UserDto dto, String passwordHash) {
        if (dto == null) {
            throw new IllegalArgumentException("UserDto must not be null");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("passwordHash must not be blank");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("email must not be blank");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("User with email already exists: " + dto.getEmail());
        }

        User entity = userMapper.toEntity(dto);
        entity.setId(null);
        entity.setPasswordHash(passwordHash);

        User saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto dto) {
        User existing = getEntityById(id);

        if (dto.getEmail() != null && !dto.getEmail().equalsIgnoreCase(existing.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("User with email already exists: " + dto.getEmail());
            }
            existing.setEmail(dto.getEmail());
        }

        if (dto.getRole() != null) {
            existing.setRole(dto.getRole());
        }
        if (dto.getIsActive() != null) {
            existing.setIsActive(dto.getIsActive());
        }

        User saved = userRepository.save(existing);
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(getEntityById(id));
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserDto getByEmail(String email) {
        return userMapper.toDto(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException("User not found by email: " + email))
        );
    }

    @Override
    public List<UserDto> getByRole(UserRole role) {
        return userMapper.toDtoList(userRepository.findAllByRole(role));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserDto activate(Long id) {
        User user = getEntityById(id);
        user.setIsActive(true);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto deactivate(Long id) {
        User user = getEntityById(id);
        user.setIsActive(false);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    private User getEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }
}