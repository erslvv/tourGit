package kz.safetrip.safetrip.repository;

import kz.safetrip.safetrip.model.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TourRepository {
    Tour save(Tour tour);

    Optional<Tour> findById(Long id);

    boolean existsById(Long id);

    Page<Tour> findAll(Pageable pageable);

    Page<Tour> findByCity(String city, Pageable pageable);

    Page<Tour> findByH3Index(String h3Index, Pageable pageable);

    Page<Tour> findByIsFeatured(Boolean isFeatured, Pageable pageable);

    Page<Tour> findByIsVerified(Boolean isVerified, Pageable pageable);

    void deleteById(Long id);

    List<Tour> findAll();

    List<Tour> findAllByCityIgnoreCase(String city);

    List<Tour> findAllByIsFeaturedTrue();

    List<Tour> findAllByIsVerifiedTrue();

    List<Tour> findAllByH3Index(String h3Index);
}