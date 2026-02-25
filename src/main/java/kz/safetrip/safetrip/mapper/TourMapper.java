package kz.safetrip.safetrip.mapper;

import kz.safetrip.safetrip.config.GlobalMapperConfig;
import kz.safetrip.safetrip.model.dto.TourDto;
import kz.safetrip.safetrip.model.entity.Tour;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface TourMapper {

    TourDto toDto(Tour entity);

    List<TourDto> toDtoList(List<Tour> entities);

    Tour toEntity(TourDto dto);

    List<Tour> toEntityList(List<TourDto> dtos);
}
