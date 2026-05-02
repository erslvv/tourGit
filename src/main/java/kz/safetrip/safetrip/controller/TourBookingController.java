package kz.safetrip.safetrip.controller;

import kz.safetrip.safetrip.model.dto.TourBookingDto;
import kz.safetrip.safetrip.security.CurrentUserService;
import kz.safetrip.safetrip.service.TourBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tour-bookings")
@RequiredArgsConstructor
public class TourBookingController {

    private final TourBookingService tourBookingService;
    private final CurrentUserService currentUserService;

    @PostMapping
    public ResponseEntity<TourBookingDto> create(@RequestBody TourBookingDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tourBookingService.create(currentUserService.getCurrentUserId(), dto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<TourBookingDto>> getCurrentUserBookings() {
        return ResponseEntity.ok(tourBookingService.getCurrentUserBookings(currentUserService.getCurrentUserId()));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelCurrentUserBooking(@PathVariable Long bookingId) {
        tourBookingService.cancel(currentUserService.getCurrentUserId(), bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TourBookingDto>> getAll() {
        return ResponseEntity.ok(tourBookingService.getAll());
    }
}
