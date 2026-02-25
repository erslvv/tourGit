package kz.safetrip.safetrip.controller;

import kz.safetrip.safetrip.model.dto.AreaPopularityDailyDto;
import kz.safetrip.safetrip.service.AreaPopularityDailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/area-popularity-daily")
@RequiredArgsConstructor
public class AreaPopularityDailyController {

    private final AreaPopularityDailyService areaPopularityDailyService;

    @PutMapping
    public ResponseEntity<AreaPopularityDailyDto> upsert(@RequestBody AreaPopularityDailyDto dto) {
        return ResponseEntity.ok(areaPopularityDailyService.upsert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaPopularityDailyDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(areaPopularityDailyService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AreaPopularityDailyDto>> getAll() {
        return ResponseEntity.ok(areaPopularityDailyService.getAll());
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<AreaPopularityDailyDto>> getByStatDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate statDate
    ) {
        return ResponseEntity.ok(areaPopularityDailyService.getByStatDate(statDate));
    }

    @GetMapping("/by-h3")
    public ResponseEntity<List<AreaPopularityDailyDto>> getByH3Index(@RequestParam String h3Index) {
        return ResponseEntity.ok(areaPopularityDailyService.getByH3Index(h3Index));
    }

    @GetMapping("/by-date-and-h3")
    public ResponseEntity<AreaPopularityDailyDto> getByStatDateAndH3Index(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate statDate,
            @RequestParam String h3Index
    ) {
        return ResponseEntity.ok(areaPopularityDailyService.getByStatDateAndH3Index(statDate, h3Index));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        areaPopularityDailyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}