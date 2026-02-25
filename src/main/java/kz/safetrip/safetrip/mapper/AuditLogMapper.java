package kz.safetrip.safetrip.mapper;

import kz.safetrip.safetrip.config.GlobalMapperConfig;
import kz.safetrip.safetrip.model.dto.AuditLogDto;
import kz.safetrip.safetrip.model.entity.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class, uses = EntityReferenceMapper.class)
public interface AuditLogMapper {

    @Mapping(target = "userId", source = "user.id")
    AuditLogDto toDto(AuditLog entity);

    List<AuditLogDto> toDtoList(List<AuditLog> entities);

    @Mapping(target = "user", source = "userId", qualifiedByName = "userFromId")
    AuditLog toEntity(AuditLogDto dto);

    List<AuditLog> toEntityList(List<AuditLogDto> dtos);
}
