package kz.safetrip.safetrip.repository;

import kz.safetrip.safetrip.model.entity.FavoritePlace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FavoritePlaceRepository {
    FavoritePlace save(FavoritePlace favoritePlace);

    Optional<FavoritePlace> findById(Long id);

    Optional<FavoritePlace> findByUserIdAndPlaceId(Long userId, Long placeId);

    boolean existsByUserIdAndPlaceId(Long userId, Long placeId);

    Page<FavoritePlace> findByUserId(Long userId, Pageable pageable);

    Page<FavoritePlace> findByPlaceId(Long placeId, Pageable pageable);

    long countByPlaceId(Long placeId);

    boolean deleteByUserIdAndPlaceId(Long userId, Long placeId);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<FavoritePlace> findAll();

    List<FavoritePlace> findAllByUser_Id(Long userId);

    List<FavoritePlace> findAllByPlace_Id(Long placeId);

    Optional<FavoritePlace> findByUser_IdAndPlace_Id(Long userId, Long placeId);

    boolean existsByUser_IdAndPlace_Id(Long userId, Long placeId);

    void deleteByUser_IdAndPlace_Id(Long userId, Long placeId);

}