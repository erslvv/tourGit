package kz.safetrip.safetrip.repository.impl;

import kz.safetrip.safetrip.enumeration.AuditEntityType;
import kz.safetrip.safetrip.enumeration.AuditStatus;
import kz.safetrip.safetrip.model.entity.AuditLog;
import kz.safetrip.safetrip.repository.AuditLogRepository;
import kz.safetrip.safetrip.repository.jpa.AuditLogJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuditLogRepositoryImpl implements AuditLogRepository {
    private final AuditLogJpaRepository auditLogJpaRepository;
    @Override @Transactional public AuditLog save(AuditLog auditLog) { return auditLogJpaRepository.save(auditLog); }
    @Override public Optional<AuditLog> findById(Long id) { return auditLogJpaRepository.findById(id); }
    @Override public Page<AuditLog> findAll(Pageable pageable) { return auditLogJpaRepository.findAll(pageable); }
    @Override public Page<AuditLog> findByUserId(Long userId, Pageable pageable) { return auditLogJpaRepository.findByUser_Id(userId, pageable); }
    @Override public Page<AuditLog> findByEntityTypeAndEntityId(AuditEntityType entityType, Long entityId, Pageable pageable) { return auditLogJpaRepository.findByEntityTypeAndEntityId(entityType, entityId, pageable); }
    @Override public Page<AuditLog> findByStatus(AuditStatus status, Pageable pageable) { return auditLogJpaRepository.findByStatus(status, pageable); }
    @Override public Page<AuditLog> findByEventTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable) { return auditLogJpaRepository.findByEventTimeBetween(from, to, pageable); }
    @Override public boolean existsById(Long id) { return auditLogJpaRepository.existsById(id); }
    @Override @Transactional public void deleteById(Long id) { auditLogJpaRepository.deleteById(id); }
    @Override public List<AuditLog> findAll() { return auditLogJpaRepository.findAll(); }
    @Override public List<AuditLog> findAllByUser_Id(Long userId) { return auditLogJpaRepository.findAllByUser_Id(userId); }
    @Override public List<AuditLog> findAllByEntityTypeAndEntityId(AuditEntityType entityType, Long entityId) { return auditLogJpaRepository.findAllByEntityTypeAndEntityId(entityType, entityId); }
    @Override public List<AuditLog> findAllByStatus(AuditStatus status) { return auditLogJpaRepository.findAllByStatus(status); }
}
