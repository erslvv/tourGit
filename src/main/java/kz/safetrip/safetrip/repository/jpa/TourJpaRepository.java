package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.model.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourJpaRepository extends JpaRepository<Tour, Long> {
    Page<Tour> findByCity(String city, Pageable pageable);
    Page<Tour> findByH3Index(String h3Index, Pageable pageable);
    Page<Tour> findByIsFeatured(Boolean isFeatured, Pageable pageable);
    Page<Tour> findByIsVerified(Boolean isVerified, Pageable pageable);
    List<Tour> findAllByCityIgnoreCase(String city);
    List<Tour> findAllByIsFeaturedTrue();
    List<Tour> findAllByIsVerifiedTrue();
    List<Tour> findAllByH3Index(String h3Index);
}
