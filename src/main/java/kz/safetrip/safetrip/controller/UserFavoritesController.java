package kz.safetrip.safetrip.controller;

import kz.safetrip.safetrip.model.dto.profile.ProfileFavoritesDto;
import kz.safetrip.safetrip.service.FavoritePlaceService;
import kz.safetrip.safetrip.service.FavoriteTourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserFavoritesController {
    private final FavoriteTourService favoriteTourService;
    private final FavoritePlaceService favoritePlaceService;

    @GetMapping("/{id}/favorites")
    public ResponseEntity<ProfileFavoritesDto> getUserFavorites(@PathVariable Long id) {
        return ResponseEntity.ok(ProfileFavoritesDto.builder().userId(id).tours(favoriteTourService.getByUserId(id)).places(favoritePlaceService.getByUserId(id)).build());
    }
}
