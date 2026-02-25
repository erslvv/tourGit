package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.model.dto.FavoriteTourDto;

import java.util.List;

public interface FavoriteTourService {

    FavoriteTourDto create(FavoriteTourDto dto);

    FavoriteTourDto getById(Long id);

    List<FavoriteTourDto> getAll();

    List<FavoriteTourDto> getByUserId(Long userId);

    List<FavoriteTourDto> getByTourId(Long tourId);

    boolean existsByUserIdAndTourId(Long userId, Long tourId);

    void deleteById(Long id);

    void deleteByUserIdAndTourId(Long userId, Long tourId);
}