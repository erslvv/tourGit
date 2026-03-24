package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.model.entity.FavoriteTour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteTourJpaRepository extends JpaRepository<FavoriteTour, Long> {
    Optional<FavoriteTour> findByUser_IdAndTour_Id(Long userId, Long tourId);
    boolean existsByUser_IdAndTour_Id(Long userId, Long tourId);
    Page<FavoriteTour> findByUser_Id(Long userId, Pageable pageable);
    Page<FavoriteTour> findByTour_Id(Long tourId, Pageable pageable);
    long countByTour_Id(Long tourId);
    long deleteByUser_IdAndTour_Id(Long userId, Long tourId);
    List<FavoriteTour> findAllByUser_Id(Long userId);
    List<FavoriteTour> findAllByTour_Id(Long tourId);
}
