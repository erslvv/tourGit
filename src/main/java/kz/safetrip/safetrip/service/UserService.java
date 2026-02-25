package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.enumeration.UserRole;
import kz.safetrip.safetrip.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto dto, String passwordHash);

    UserDto update(Long id, UserDto dto);

    UserDto getById(Long id);

    List<UserDto> getAll();

    UserDto getByEmail(String email);

    List<UserDto> getByRole(UserRole role);

    boolean existsByEmail(String email);

    UserDto activate(Long id);

    UserDto deactivate(Long id);

    void delete(Long id);
}