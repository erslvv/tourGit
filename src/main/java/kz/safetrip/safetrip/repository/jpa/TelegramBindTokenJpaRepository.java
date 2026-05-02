package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.model.entity.TelegramBindToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TelegramBindTokenJpaRepository extends JpaRepository<TelegramBindToken, Long> {

    Optional<TelegramBindToken> findByCodeHash(String codeHash);

    @Modifying
    @Query("""
            update TelegramBindToken t
            set t.usedAt = :usedAt
            where t.user.id = :userId
              and t.usedAt is null
            """)
    void markActiveTokensAsUsed(Long userId, LocalDateTime usedAt);
}
