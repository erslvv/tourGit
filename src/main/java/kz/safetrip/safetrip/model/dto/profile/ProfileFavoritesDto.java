package kz.safetrip.safetrip.model.dto.profile;

import kz.safetrip.safetrip.model.dto.FavoritePlaceDto;
import kz.safetrip.safetrip.model.dto.FavoriteTourDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ProfileFavoritesDto {
    private Long userId;
    private List<FavoriteTourDto> tours;
    private List<FavoritePlaceDto> places;
}
