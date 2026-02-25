package kz.safetrip.safetrip.repository;

import kz.safetrip.safetrip.model.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository {
    Place save(Place place);

    Optional<Place> findById(Long id);

    boolean existsById(Long id);

    Page<Place> findAll(Pageable pageable);

    Page<Place> findByCity(String city, Pageable pageable);

    Page<Place> findByCategory(String category, Pageable pageable);

    Page<Place> findByH3Index(String h3Index, Pageable pageable);

    Page<Place> findByIsFeatured(Boolean isFeatured, Pageable pageable);

    Page<Place> findByIsVerified(Boolean isVerified, Pageable pageable);

    void deleteById(Long id);

    List<Place> findAll();

    List<Place> findAllByCityIgnoreCase(String city);

    List<Place> findAllByCategoryIgnoreCase(String category);

    List<Place> findAllByIsFeaturedTrue();

    List<Place> findAllByIsVerifiedTrue();

    List<Place> findAllByH3Index(String h3Index);
}