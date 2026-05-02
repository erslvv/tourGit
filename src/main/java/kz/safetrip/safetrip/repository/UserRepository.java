package kz.safetrip.safetrip.repository;

import kz.safetrip.safetrip.enumeration.UserRole;
import kz.safetrip.safetrip.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    boolean existsById(Long id);

    Page<User> findAll(Pageable pageable);

    Page<User> findByRole(UserRole role, Pageable pageable);

    Page<User> findByIsActive(Boolean isActive, Pageable pageable);

    void deleteById(Long id);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByTelegramChatId(String telegramChatId);

    boolean existsByEmail(String email);

    List<User> findAllByRole(UserRole role);
}