package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.model.dto.TourBookingDto;

import java.util.List;

public interface TourBookingService {
    TourBookingDto create(Long userId, TourBookingDto dto);

    List<TourBookingDto> getCurrentUserBookings(Long userId);

    List<TourBookingDto> getAll();

    void cancel(Long userId, Long bookingId);
}
