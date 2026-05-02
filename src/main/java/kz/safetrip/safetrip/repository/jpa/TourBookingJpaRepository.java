package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.model.entity.TourBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourBookingJpaRepository extends JpaRepository<TourBooking, Long> {
    List<TourBooking> findAllByUser_IdOrderByCreatedAtDesc(Long userId);
    boolean existsByUser_IdAndTour_Id(Long userId, Long tourId);
    long countByTour_Id(Long tourId);
}
