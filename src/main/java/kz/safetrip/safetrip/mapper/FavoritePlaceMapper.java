package kz.safetrip.safetrip.mapper;

import kz.safetrip.safetrip.config.GlobalMapperConfig;
import kz.safetrip.safetrip.model.dto.FavoritePlaceDto;
import kz.safetrip.safetrip.model.entity.FavoritePlace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class, uses = EntityReferenceMapper.class)
public interface FavoritePlaceMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "placeId", source = "place.id")
    FavoritePlaceDto toDto(FavoritePlace entity);

    List<FavoritePlaceDto> toDtoList(List<FavoritePlace> entities);

    @Mapping(target = "user", source = "userId", qualifiedByName = "userFromId")
    @Mapping(target = "place", source = "placeId", qualifiedByName = "placeFromId")
    FavoritePlace toEntity(FavoritePlaceDto dto);

    List<FavoritePlace> toEntityList(List<FavoritePlaceDto> dtos);
}
