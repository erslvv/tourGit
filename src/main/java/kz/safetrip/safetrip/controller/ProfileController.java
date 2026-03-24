package kz.safetrip.safetrip.controller;

import kz.safetrip.safetrip.model.dto.FavoritePlaceDto;
import kz.safetrip.safetrip.model.dto.FavoriteTourDto;
import kz.safetrip.safetrip.model.dto.profile.ProfileFavoritesDto;
import kz.safetrip.safetrip.security.CurrentUserService;
import kz.safetrip.safetrip.service.FavoritePlaceService;
import kz.safetrip.safetrip.service.FavoriteTourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final CurrentUserService currentUserService;
    private final FavoriteTourService favoriteTourService;
    private final FavoritePlaceService favoritePlaceService;

    @GetMapping("/favorites")
    public ResponseEntity<ProfileFavoritesDto> getFavorites() {
        Long userId = currentUserService.getCurrentUserId();
        return ResponseEntity.ok(ProfileFavoritesDto.builder().userId(userId).tours(favoriteTourService.getByUserId(userId)).places(favoritePlaceService.getByUserId(userId)).build());
    }

    @PostMapping("/favorites/tours/{tourId}")
    public ResponseEntity<FavoriteTourDto> addFavoriteTour(@PathVariable Long tourId) {
        FavoriteTourDto dto = FavoriteTourDto.builder().userId(currentUserService.getCurrentUserId()).tourId(tourId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteTourService.create(dto));
    }

    @DeleteMapping("/favorites/tours/{tourId}")
    public ResponseEntity<Void> deleteFavoriteTour(@PathVariable Long tourId) {
        favoriteTourService.deleteByUserIdAndTourId(currentUserService.getCurrentUserId(), tourId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/favorites/places/{placeId}")
    public ResponseEntity<FavoritePlaceDto> addFavoritePlace(@PathVariable Long placeId) {
        FavoritePlaceDto dto = FavoritePlaceDto.builder().userId(currentUserService.getCurrentUserId()).placeId(placeId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritePlaceService.create(dto));
    }

    @DeleteMapping("/favorites/places/{placeId}")
    public ResponseEntity<Void> deleteFavoritePlace(@PathVariable Long placeId) {
        favoritePlaceService.deleteByUserIdAndPlaceId(currentUserService.getCurrentUserId(), placeId);
        return ResponseEntity.noContent().build();
    }
}
