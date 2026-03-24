package kz.safetrip.safetrip.repository.impl;

import kz.safetrip.safetrip.model.entity.FavoritePlace;
import kz.safetrip.safetrip.repository.FavoritePlaceRepository;
import kz.safetrip.safetrip.repository.jpa.FavoritePlaceJpaRepository;
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
public class FavoritePlaceRepositoryImpl implements FavoritePlaceRepository {
    private final FavoritePlaceJpaRepository favoritePlaceJpaRepository;
    @Override @Transactional public FavoritePlace save(FavoritePlace favoritePlace) { return favoritePlaceJpaRepository.save(favoritePlace); }
    @Override public Optional<FavoritePlace> findById(Long id) { return favoritePlaceJpaRepository.findById(id); }
    @Override public Optional<FavoritePlace> findByUserIdAndPlaceId(Long userId, Long placeId) { return favoritePlaceJpaRepository.findByUser_IdAndPlace_Id(userId, placeId); }
    @Override public boolean existsByUserIdAndPlaceId(Long userId, Long placeId) { return favoritePlaceJpaRepository.existsByUser_IdAndPlace_Id(userId, placeId); }
    @Override public Page<FavoritePlace> findByUserId(Long userId, Pageable pageable) { return favoritePlaceJpaRepository.findByUser_Id(userId, pageable); }
    @Override public Page<FavoritePlace> findByPlaceId(Long placeId, Pageable pageable) { return favoritePlaceJpaRepository.findByPlace_Id(placeId, pageable); }
    @Override public long countByPlaceId(Long placeId) { return favoritePlaceJpaRepository.countByPlace_Id(placeId); }
    @Override @Transactional public boolean deleteByUserIdAndPlaceId(Long userId, Long placeId) { return favoritePlaceJpaRepository.deleteByUser_IdAndPlace_Id(userId, placeId) > 0; }
    @Override @Transactional public void deleteById(Long id) { favoritePlaceJpaRepository.deleteById(id); }
    @Override public boolean existsById(Long id) { return favoritePlaceJpaRepository.existsById(id); }
    @Override public List<FavoritePlace> findAll() { return favoritePlaceJpaRepository.findAll(); }
    @Override public List<FavoritePlace> findAllByUser_Id(Long userId) { return favoritePlaceJpaRepository.findAllByUser_Id(userId); }
    @Override public List<FavoritePlace> findAllByPlace_Id(Long placeId) { return favoritePlaceJpaRepository.findAllByPlace_Id(placeId); }
    @Override public Optional<FavoritePlace> findByUser_IdAndPlace_Id(Long userId, Long placeId) { return favoritePlaceJpaRepository.findByUser_IdAndPlace_Id(userId, placeId); }
    @Override public boolean existsByUser_IdAndPlace_Id(Long userId, Long placeId) { return favoritePlaceJpaRepository.existsByUser_IdAndPlace_Id(userId, placeId); }
    @Override @Transactional public void deleteByUser_IdAndPlace_Id(Long userId, Long placeId) { favoritePlaceJpaRepository.deleteByUser_IdAndPlace_Id(userId, placeId); }
}
