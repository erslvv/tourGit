package kz.safetrip.safetrip.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.safetrip.safetrip.enumeration.AuditEntityType;
import kz.safetrip.safetrip.enumeration.AuditStatus;
import kz.safetrip.safetrip.mapper.AuditLogMapper;
import kz.safetrip.safetrip.model.dto.AuditLogDto;
import kz.safetrip.safetrip.model.entity.AuditLog;
import kz.safetrip.safetrip.repository.AuditLogRepository;
import kz.safetrip.safetrip.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    @Transactional
    public AuditLogDto create(AuditLogDto dto) {
        AuditLog entity = auditLogMapper.toEntity(dto);
        entity.setId(null);
        AuditLog saved = auditLogRepository.save(entity);
        return auditLogMapper.toDto(saved);
    }

    @Override
    public AuditLogDto getById(Long id) {
        return auditLogMapper.toDto(getEntityById(id));
    }

    @Override
    public List<AuditLogDto> getAll() {
        return auditLogMapper.toDtoList(auditLogRepository.findAll());
    }

    @Override
    public List<AuditLogDto> getByUserId(Long userId) {
        return auditLogMapper.toDtoList(auditLogRepository.findAllByUser_Id(userId));
    }

    @Override
    public List<AuditLogDto> getByEntity(AuditEntityType entityType, Long entityId) {
        return auditLogMapper.toDtoList(auditLogRepository.findAllByEntityTypeAndEntityId(entityType, entityId));
    }

    @Override
    public List<AuditLogDto> getByStatus(AuditStatus status) {
        return auditLogMapper.toDtoList(auditLogRepository.findAllByStatus(status));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!auditLogRepository.existsById(id)) {
            throw new EntityNotFoundException("AuditLog not found: " + id);
        }
        auditLogRepository.deleteById(id);
    }

    private AuditLog getEntityById(Long id) {
        return auditLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AuditLog not found: " + id));
    }
}