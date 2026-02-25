package kz.safetrip.safetrip.controller;

import kz.safetrip.safetrip.model.dto.PlaceDto;
import kz.safetrip.safetrip.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<PlaceDto> create(@RequestBody PlaceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(placeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDto> update(@PathVariable Long id, @RequestBody PlaceDto dto) {
        return ResponseEntity.ok(placeService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(placeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlaceDto>> getAll() {
        return ResponseEntity.ok(placeService.getAll());
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<PlaceDto>> getByCity(@RequestParam String city) {
        return ResponseEntity.ok(placeService.getByCity(city));
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<PlaceDto>> getByCategory(@RequestParam String category) {
        return ResponseEntity.ok(placeService.getByCategory(category));
    }

    @GetMapping("/featured")
    public ResponseEntity<List<PlaceDto>> getFeatured() {
        return ResponseEntity.ok(placeService.getFeatured());
    }

    @GetMapping("/verified")
    public ResponseEntity<List<PlaceDto>> getVerified() {
        return ResponseEntity.ok(placeService.getVerified());
    }

    @GetMapping("/by-h3")
    public ResponseEntity<List<PlaceDto>> getByH3Index(@RequestParam String h3Index) {
        return ResponseEntity.ok(placeService.getByH3Index(h3Index));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        placeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}