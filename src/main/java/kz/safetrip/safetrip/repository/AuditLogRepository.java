package kz.safetrip.safetrip.repository;

import kz.safetrip.safetrip.enumeration.AuditEntityType;
import kz.safetrip.safetrip.enumeration.AuditStatus;
import kz.safetrip.safetrip.model.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuditLogRepository {
    AuditLog save(AuditLog auditLog);

    Optional<AuditLog> findById(Long id);

    Page<AuditLog> findAll(Pageable pageable);

    Page<AuditLog> findByUserId(Long userId, Pageable pageable);

    Page<AuditLog> findByEntityTypeAndEntityId(AuditEntityType entityType, Long entityId, Pageable pageable);

    Page<AuditLog> findByStatus(AuditStatus status, Pageable pageable);

    Page<AuditLog> findByEventTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<AuditLog> findAll();

    List<AuditLog> findAllByUser_Id(Long userId);

    List<AuditLog> findAllByEntityTypeAndEntityId(AuditEntityType entityType, Long entityId);

    List<AuditLog> findAllByStatus(AuditStatus status);
}