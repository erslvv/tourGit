package kz.safetrip.safetrip.repository.impl;

import kz.safetrip.safetrip.model.entity.TourBooking;
import kz.safetrip.safetrip.repository.TourBookingRepository;
import kz.safetrip.safetrip.repository.jpa.TourBookingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TourBookingRepositoryImpl implements TourBookingRepository {
    private final TourBookingJpaRepository tourBookingJpaRepository;

    @Override
    @Transactional
    public TourBooking save(TourBooking booking) {
        return tourBookingJpaRepository.save(booking);
    }

    @Override
    public TourBooking findById(Long id) {
        return tourBookingJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<TourBooking> findAll() {
        return tourBookingJpaRepository.findAll();
    }

    @Override
    public List<TourBooking> findAllByUserIdOrderByCreatedAtDesc(Long userId) {
        return tourBookingJpaRepository.findAllByUser_IdOrderByCreatedAtDesc(userId);
    }

    @Override
    public boolean existsByUserIdAndTourId(Long userId, Long tourId) {
        return tourBookingJpaRepository.existsByUser_IdAndTour_Id(userId, tourId);
    }

    @Override
    public long countByTourId(Long tourId) {
        return tourBookingJpaRepository.countByTour_Id(tourId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tourBookingJpaRepository.deleteById(id);
    }
}
