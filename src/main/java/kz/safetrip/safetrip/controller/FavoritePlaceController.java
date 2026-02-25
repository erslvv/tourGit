package kz.safetrip.safetrip.controller;

import kz.safetrip.safetrip.model.dto.FavoritePlaceDto;
import kz.safetrip.safetrip.service.FavoritePlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite-places")
@RequiredArgsConstructor
public class FavoritePlaceController {

    private final FavoritePlaceService favoritePlaceService;

    @PostMapping
    public ResponseEntity<FavoritePlaceDto> create(@RequestBody FavoritePlaceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritePlaceService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritePlaceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(favoritePlaceService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<FavoritePlaceDto>> getAll() {
        return ResponseEntity.ok(favoritePlaceService.getAll());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<FavoritePlaceDto>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(favoritePlaceService.getByUserId(userId));
    }

    @GetMapping("/by-place/{placeId}")
    public ResponseEntity<List<FavoritePlaceDto>> getByPlaceId(@PathVariable Long placeId) {
        return ResponseEntity.ok(favoritePlaceService.getByPlaceId(placeId));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists(@RequestParam Long userId, @RequestParam Long placeId) {
        return ResponseEntity.ok(favoritePlaceService.existsByUserIdAndPlaceId(userId, placeId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        favoritePlaceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByUserAndPlace(@RequestParam Long userId, @RequestParam Long placeId) {
        favoritePlaceService.deleteByUserIdAndPlaceId(userId, placeId);
        return ResponseEntity.noContent().build();
    }
}