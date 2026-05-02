package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.model.entity.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetOtpJpaRepository extends JpaRepository<PasswordResetOtp, Long> {

    Optional<PasswordResetOtp> findTopByUser_IdAndUsedAtIsNullOrderByCreatedAtDesc(Long userId);

    @Modifying
    @Query("""
            update PasswordResetOtp o
            set o.usedAt = :usedAt
            where o.user.id = :userId
              and o.usedAt is null
            """)
    void markActiveOtpsAsUsed(Long userId, LocalDateTime usedAt);
}
