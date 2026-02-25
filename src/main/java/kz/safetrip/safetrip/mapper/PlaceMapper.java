package kz.safetrip.safetrip.mapper;

import kz.safetrip.safetrip.config.GlobalMapperConfig;
import kz.safetrip.safetrip.model.dto.PlaceDto;
import kz.safetrip.safetrip.model.entity.Place;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface PlaceMapper {

    PlaceDto toDto(Place entity);

    List<PlaceDto> toDtoList(List<Place> entities);

    Place toEntity(PlaceDto dto);

    List<Place> toEntityList(List<PlaceDto> dtos);
}
