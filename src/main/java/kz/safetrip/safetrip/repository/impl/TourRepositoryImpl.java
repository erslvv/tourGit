package kz.safetrip.safetrip.repository.impl;

import kz.safetrip.safetrip.model.entity.Tour;
import kz.safetrip.safetrip.repository.TourRepository;
import kz.safetrip.safetrip.repository.jpa.TourJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TourRepositoryImpl implements TourRepository {

    private final TourJpaRepository tourJpaRepository;

    @Override
    @Transactional
    public Tour save(Tour tour) {
        return tourJpaRepository.save(tour);
    }

    @Override
    public Optional<Tour> findById(Long id) {
        return tourJpaRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return tourJpaRepository.existsById(id);
    }

    @Override
    public Page<Tour> findAll(Pageable pageable) {
        return tourJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Tour> findByCity(String city, Pageable pageable) {
        return tourJpaRepository.findByCity(city, pageable);
    }

    @Override
    public Page<Tour> findByH3Index(String h3Index, Pageable pageable) {
        return tourJpaRepository.findByH3Index(h3Index, pageable);
    }

    @Override
    public Page<Tour> findByIsFeatured(Boolean isFeatured, Pageable pageable) {
        return tourJpaRepository.findByIsFeatured(isFeatured, pageable);
    }

    @Override
    public Page<Tour> findByIsVerified(Boolean isVerified, Pageable pageable) {
        return tourJpaRepository.findByIsVerified(isVerified, pageable);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tourJpaRepository.deleteById(id);
    }

    @Override
    public List<Tour> findAll() {
        return List.of();
    }

    @Override
    public List<Tour> findAllByCityIgnoreCase(String city) {
        return List.of();
    }

    @Override
    public List<Tour> findAllByIsFeaturedTrue() {
        return List.of();
    }

    @Override
    public List<Tour> findAllByIsVerifiedTrue() {
        return List.of();
    }

    @Override
    public List<Tour> findAllByH3Index(String h3Index) {
        return List.of();
    }
}