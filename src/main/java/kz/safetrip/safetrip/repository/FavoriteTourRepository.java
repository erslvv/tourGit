package kz.safetrip.safetrip.repository;

import kz.safetrip.safetrip.model.entity.FavoriteTour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FavoriteTourRepository {
    FavoriteTour save(FavoriteTour favoriteTour);

    Optional<FavoriteTour> findById(Long id);

    Optional<FavoriteTour> findByUserIdAndTourId(Long userId, Long tourId);

    boolean existsByUserIdAndTourId(Long userId, Long tourId);

    Page<FavoriteTour> findByUserId(Long userId, Pageable pageable);

    Page<FavoriteTour> findByTourId(Long tourId, Pageable pageable);

    long countByTourId(Long tourId);

    boolean deleteByUserIdAndTourId(Long userId, Long tourId);

    void deleteById(Long id);

    List<FavoriteTour> findAll();

    boolean existsById(Long id);

    List<FavoriteTour> findAllByUser_Id(Long userId);

    List<FavoriteTour> findAllByTour_Id(Long tourId);

    Optional<FavoriteTour> findByUser_IdAndTour_Id(Long userId, Long tourId);

    boolean existsByUser_IdAndTour_Id(Long userId, Long tourId);

    void deleteByUser_IdAndTour_Id(Long userId, Long tourId);
}