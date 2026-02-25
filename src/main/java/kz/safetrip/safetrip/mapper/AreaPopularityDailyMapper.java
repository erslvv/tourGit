package kz.safetrip.safetrip.mapper;

import kz.safetrip.safetrip.config.GlobalMapperConfig;
import kz.safetrip.safetrip.model.dto.AreaPopularityDailyDto;
import kz.safetrip.safetrip.model.entity.AreaPopularityDaily;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface AreaPopularityDailyMapper {

    AreaPopularityDailyDto toDto(AreaPopularityDaily entity);

    List<AreaPopularityDailyDto> toDtoList(List<AreaPopularityDaily> entities);

    AreaPopularityDaily toEntity(AreaPopularityDailyDto dto);

    List<AreaPopularityDaily> toEntityList(List<AreaPopularityDailyDto> dtos);
}
