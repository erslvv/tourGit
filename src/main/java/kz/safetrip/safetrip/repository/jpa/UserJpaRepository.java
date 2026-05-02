package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.enumeration.UserRole;
import kz.safetrip.safetrip.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByTelegramChatId(String telegramChatId);
    boolean existsByEmail(String email);
    Page<User> findByRole(UserRole role, Pageable pageable);
    Page<User> findByIsActive(Boolean isActive, Pageable pageable);
    List<User> findAllByRole(UserRole role);
}
