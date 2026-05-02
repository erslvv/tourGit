package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetTokenJpaRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            update PasswordResetToken token
            set token.usedAt = :usedAt
            where token.user.id = :userId
              and token.usedAt is null
            """)
    int markActiveTokensAsUsed(@Param("userId") Long userId, @Param("usedAt") LocalDateTime usedAt);
}
