package kz.safetrip.safetrip.repository.impl;

import kz.safetrip.safetrip.model.entity.FavoriteTour;
import kz.safetrip.safetrip.repository.FavoriteTourRepository;
import kz.safetrip.safetrip.repository.jpa.FavoriteTourJpaRepository;
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
public class FavoriteTourRepositoryImpl implements FavoriteTourRepository {

    private final FavoriteTourJpaRepository favoriteTourJpaRepository;

    @Override
    @Transactional
    public FavoriteTour save(FavoriteTour favoriteTour) {
        return favoriteTourJpaRepository.save(favoriteTour);
    }

    @Override
    public Optional<FavoriteTour> findById(Long id) {
        return favoriteTourJpaRepository.findById(id);
    }

    @Override
    public Optional<FavoriteTour> findByUserIdAndTourId(Long userId, Long tourId) {
        return favoriteTourJpaRepository.findByUser_IdAndTour_Id(userId, tourId);
    }

    @Override
    public boolean existsByUserIdAndTourId(Long userId, Long tourId) {
        return favoriteTourJpaRepository.existsByUser_IdAndTour_Id(userId, tourId);
    }

    @Override
    public Page<FavoriteTour> findByUserId(Long userId, Pageable pageable) {
        return favoriteTourJpaRepository.findByUser_Id(userId, pageable);
    }

    @Override
    public Page<FavoriteTour> findByTourId(Long tourId, Pageable pageable) {
        return favoriteTourJpaRepository.findByTour_Id(tourId, pageable);
    }

    @Override
    public long countByTourId(Long tourId) {
        return favoriteTourJpaRepository.countByTour_Id(tourId);
    }

    @Override
    @Transactional
    public boolean deleteByUserIdAndTourId(Long userId, Long tourId) {
        return favoriteTourJpaRepository.deleteByUser_IdAndTour_Id(userId, tourId) > 0;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        favoriteTourJpaRepository.deleteById(id);
    }

    @Override
    public List<FavoriteTour> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public List<FavoriteTour> findAllByUser_Id(Long userId) {
        return List.of();
    }

    @Override
    public List<FavoriteTour> findAllByTour_Id(Long tourId) {
        return List.of();
    }

    @Override
    public Optional<FavoriteTour> findByUser_IdAndTour_Id(Long userId, Long tourId) {
        return Optional.empty();
    }

    @Override
    public boolean existsByUser_IdAndTour_Id(Long userId, Long tourId) {
        return false;
    }

    @Override
    public void deleteByUser_IdAndTour_Id(Long userId, Long tourId) {

    }
}