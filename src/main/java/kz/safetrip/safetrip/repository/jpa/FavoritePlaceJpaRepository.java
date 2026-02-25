package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.model.entity.FavoritePlace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FavoritePlaceJpaRepository extends JpaRepository<FavoritePlace, Long> {
    Optional<FavoritePlace> findByUser_IdAndPlace_Id(Long userId, Long placeId);

    boolean existsByUser_IdAndPlace_Id(Long userId, Long placeId);

    Page<FavoritePlace> findByUser_Id(Long userId, Pageable pageable);

    Page<FavoritePlace> findByPlace_Id(Long placeId, Pageable pageable);

    long countByPlace_Id(Long placeId);

    long deleteByUser_IdAndPlace_Id(Long userId, Long placeId);
}