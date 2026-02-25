package kz.safetrip.safetrip.mapper;

import kz.safetrip.safetrip.config.GlobalMapperConfig;
import kz.safetrip.safetrip.model.dto.FavoriteTourDto;
import kz.safetrip.safetrip.model.entity.FavoriteTour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class, uses = EntityReferenceMapper.class)
public interface FavoriteTourMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "tourId", source = "tour.id")
    FavoriteTourDto toDto(FavoriteTour entity);

    List<FavoriteTourDto> toDtoList(List<FavoriteTour> entities);

    @Mapping(target = "user", source = "userId", qualifiedByName = "userFromId")
    @Mapping(target = "tour", source = "tourId", qualifiedByName = "tourFromId")
    FavoriteTour toEntity(FavoriteTourDto dto);

    List<FavoriteTour> toEntityList(List<FavoriteTourDto> dtos);
}
