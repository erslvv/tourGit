package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.model.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceJpaRepository extends JpaRepository<Place, Long> {
    Page<Place> findByCity(String city, Pageable pageable);

    Page<Place> findByCategory(String category, Pageable pageable);

    Page<Place> findByH3Index(String h3Index, Pageable pageable);

    Page<Place> findByIsFeatured(Boolean isFeatured, Pageable pageable);

    Page<Place> findByIsVerified(Boolean isVerified, Pageable pageable);
}