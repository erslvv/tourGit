package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.enumeration.AuditEntityType;
import kz.safetrip.safetrip.enumeration.AuditStatus;
import kz.safetrip.safetrip.model.dto.AuditLogDto;

import java.util.List;

public interface AuditLogService {

    AuditLogDto create(AuditLogDto dto);

    AuditLogDto getById(Long id);

    List<AuditLogDto> getAll();

    List<AuditLogDto> getByUserId(Long userId);

    List<AuditLogDto> getByEntity(AuditEntityType entityType, Long entityId);

    List<AuditLogDto> getByStatus(AuditStatus status);

    void delete(Long id);
}