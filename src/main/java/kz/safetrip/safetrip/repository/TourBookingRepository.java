package kz.safetrip.safetrip.repository;

import kz.safetrip.safetrip.model.entity.TourBooking;

import java.util.List;

public interface TourBookingRepository {
    TourBooking save(TourBooking booking);

    TourBooking findById(Long id);

    List<TourBooking> findAll();

    List<TourBooking> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    boolean existsByUserIdAndTourId(Long userId, Long tourId);

    long countByTourId(Long tourId);

    void deleteById(Long id);
}
