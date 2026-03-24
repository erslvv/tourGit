package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.enumeration.AuditEntityType;
import kz.safetrip.safetrip.enumeration.AuditStatus;
import kz.safetrip.safetrip.model.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogJpaRepository extends JpaRepository<AuditLog, Long> {
    Page<AuditLog> findByUser_Id(Long userId, Pageable pageable);
    Page<AuditLog> findByEntityTypeAndEntityId(AuditEntityType entityType, Long entityId, Pageable pageable);
    Page<AuditLog> findByStatus(AuditStatus status, Pageable pageable);
    Page<AuditLog> findByEventTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
    List<AuditLog> findAllByUser_Id(Long userId);
    List<AuditLog> findAllByEntityTypeAndEntityId(AuditEntityType entityType, Long entityId);
    List<AuditLog> findAllByStatus(AuditStatus status);
}
