package kz.safetrip.safetrip.repository.impl;

import kz.safetrip.safetrip.model.entity.Place;
import kz.safetrip.safetrip.repository.PlaceRepository;
import kz.safetrip.safetrip.repository.jpa.PlaceJpaRepository;
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
public class PlaceRepositoryImpl implements PlaceRepository {
    private final PlaceJpaRepository placeJpaRepository;
    @Override @Transactional public Place save(Place place) { return placeJpaRepository.save(place); }
    @Override public Optional<Place> findById(Long id) { return placeJpaRepository.findById(id); }
    @Override public boolean existsById(Long id) { return placeJpaRepository.existsById(id); }
    @Override public Page<Place> findAll(Pageable pageable) { return placeJpaRepository.findAll(pageable); }
    @Override public Page<Place> findByCity(String city, Pageable pageable) { return placeJpaRepository.findByCity(city, pageable); }
    @Override public Page<Place> findByCategory(String category, Pageable pageable) { return placeJpaRepository.findByCategory(category, pageable); }
    @Override public Page<Place> findByH3Index(String h3Index, Pageable pageable) { return placeJpaRepository.findByH3Index(h3Index, pageable); }
    @Override public Page<Place> findByIsFeatured(Boolean isFeatured, Pageable pageable) { return placeJpaRepository.findByIsFeatured(isFeatured, pageable); }
    @Override public Page<Place> findByIsVerified(Boolean isVerified, Pageable pageable) { return placeJpaRepository.findByIsVerified(isVerified, pageable); }
    @Override @Transactional public void deleteById(Long id) { placeJpaRepository.deleteById(id); }
    @Override public List<Place> findAll() { return placeJpaRepository.findAll(); }
    @Override public List<Place> findAllByCityIgnoreCase(String city) { return placeJpaRepository.findAllByCityIgnoreCase(city); }
    @Override public List<Place> findAllByCategoryIgnoreCase(String category) { return placeJpaRepository.findAllByCategoryIgnoreCase(category); }
    @Override public List<Place> findAllByIsFeaturedTrue() { return placeJpaRepository.findAllByIsFeaturedTrue(); }
    @Override public List<Place> findAllByIsVerifiedTrue() { return placeJpaRepository.findAllByIsVerifiedTrue(); }
    @Override public List<Place> findAllByH3Index(String h3Index) { return placeJpaRepository.findAllByH3Index(h3Index); }
}
