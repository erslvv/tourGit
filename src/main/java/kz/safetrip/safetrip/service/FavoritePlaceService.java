package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.model.dto.FavoritePlaceDto;

import java.util.List;

public interface FavoritePlaceService {

    FavoritePlaceDto create(FavoritePlaceDto dto);

    FavoritePlaceDto getById(Long id);

    List<FavoritePlaceDto> getAll();

    List<FavoritePlaceDto> getByUserId(Long userId);

    List<FavoritePlaceDto> getByPlaceId(Long placeId);

    boolean existsByUserIdAndPlaceId(Long userId, Long placeId);

    void deleteById(Long id);

    void deleteByUserIdAndPlaceId(Long userId, Long placeId);
}