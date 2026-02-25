package kz.safetrip.safetrip.controller;

import kz.safetrip.safetrip.model.dto.TourDto;
import kz.safetrip.safetrip.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @PostMapping
    public ResponseEntity<TourDto> create(@RequestBody TourDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tourService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDto> update(@PathVariable Long id, @RequestBody TourDto dto) {
        return ResponseEntity.ok(tourService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TourDto>> getAll() {
        return ResponseEntity.ok(tourService.getAll());
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<TourDto>> getByCity(@RequestParam String city) {
        return ResponseEntity.ok(tourService.getByCity(city));
    }

    @GetMapping("/featured")
    public ResponseEntity<List<TourDto>> getFeatured() {
        return ResponseEntity.ok(tourService.getFeatured());
    }

    @GetMapping("/verified")
    public ResponseEntity<List<TourDto>> getVerified() {
        return ResponseEntity.ok(tourService.getVerified());
    }

    @GetMapping("/by-h3")
    public ResponseEntity<List<TourDto>> getByH3Index(@RequestParam String h3Index) {
        return ResponseEntity.ok(tourService.getByH3Index(h3Index));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }
}