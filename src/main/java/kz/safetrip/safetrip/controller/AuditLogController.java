package kz.safetrip.safetrip.controller;

import kz.safetrip.safetrip.enumeration.AuditEntityType;
import kz.safetrip.safetrip.enumeration.AuditStatus;
import kz.safetrip.safetrip.model.dto.AuditLogDto;
import kz.safetrip.safetrip.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLogDto> create(@RequestBody AuditLogDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auditLogService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(auditLogService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AuditLogDto>> getAll() {
        return ResponseEntity.ok(auditLogService.getAll());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<AuditLogDto>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(auditLogService.getByUserId(userId));
    }

    @GetMapping("/by-entity")
    public ResponseEntity<List<AuditLogDto>> getByEntity(
            @RequestParam AuditEntityType entityType,
            @RequestParam Long entityId
    ) {
        return ResponseEntity.ok(auditLogService.getByEntity(entityType, entityId));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<AuditLogDto>> getByStatus(@RequestParam AuditStatus status) {
        return ResponseEntity.ok(auditLogService.getByStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        auditLogService.delete(id);
        return ResponseEntity.noContent().build();
    }
}