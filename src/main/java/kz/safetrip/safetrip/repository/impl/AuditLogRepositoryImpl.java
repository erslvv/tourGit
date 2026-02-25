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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuditLogRepositoryImpl implements AuditLogRepository {

    private final AuditLogJpaRepository auditLogJpaRepository;

    @Override
    public AuditLog save(AuditLog auditLog) {
        return auditLogJpaRepository.save(auditLog);
    }

    @Override
    public Optional<AuditLog> findById(Long id) {
        return auditLogJpaRepository.findById(id);
    }

    @Override
    public Page<AuditLog> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<AuditLog> findByUserId(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<AuditLog> findByEntityTypeAndEntityId(AuditEntityType entityType, Long entityId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<AuditLog> findByStatus(AuditStatus status, Pageable pageable) {
        return null;
    }

    @Override
    public Page<AuditLog> findByEventTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public List<AuditLog> findAll() {
        return auditLogJpaRepository.findAll();
    }

    @Override
    public List<AuditLog> findAllByUser_Id(Long userId) {
        return List.of();
    }

    @Override
    public List<AuditLog> findAllByEntityTypeAndEntityId(AuditEntityType entityType, Long entityId) {
        return List.of();
    }

    @Override
    public List<AuditLog> findAllByStatus(AuditStatus status) {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {
        auditLogJpaRepository.deleteById(id);
    }
}