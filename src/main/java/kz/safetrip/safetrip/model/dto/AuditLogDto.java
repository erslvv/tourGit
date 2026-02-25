package kz.safetrip.safetrip.model.dto;

import kz.safetrip.safetrip.enumeration.AuditEntityType;
import kz.safetrip.safetrip.enumeration.AuditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDto {
    private Long id;
    private LocalDateTime eventTime;
    private Long userId; // nullable
    private String action;
    private AuditEntityType entityType; // nullable
    private Long entityId; // nullable
    private AuditStatus status;
    private String ipAddress;
    private Map<String, Object> detailsJson;
}
