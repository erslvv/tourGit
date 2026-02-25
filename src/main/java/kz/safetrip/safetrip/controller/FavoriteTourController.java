package kz.safetrip.safetrip.controller;

import kz.safetrip.safetrip.model.dto.FavoriteTourDto;
import kz.safetrip.safetrip.service.FavoriteTourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite-tours")
@RequiredArgsConstructor
public class FavoriteTourController {

    private final FavoriteTourService favoriteTourService;

    @PostMapping
    public ResponseEntity<FavoriteTourDto> create(@RequestBody FavoriteTourDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteTourService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteTourDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteTourService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<FavoriteTourDto>> getAll() {
        return ResponseEntity.ok(favoriteTourService.getAll());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<FavoriteTourDto>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteTourService.getByUserId(userId));
    }

    @GetMapping("/by-tour/{tourId}")
    public ResponseEntity<List<FavoriteTourDto>> getByTourId(@PathVariable Long tourId) {
        return ResponseEntity.ok(favoriteTourService.getByTourId(tourId));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists(@RequestParam Long userId, @RequestParam Long tourId) {
        return ResponseEntity.ok(favoriteTourService.existsByUserIdAndTourId(userId, tourId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        favoriteTourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByUserAndTour(@RequestParam Long userId, @RequestParam Long tourId) {
        favoriteTourService.deleteByUserIdAndTourId(userId, tourId);
        return ResponseEntity.noContent().build();
    }
}