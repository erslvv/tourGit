package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.model.dto.TourDto;

import java.util.List;

public interface TourService {

    TourDto create(TourDto dto);

    TourDto update(Long id, TourDto dto);

    TourDto getById(Long id);

    List<TourDto> getAll();

    List<TourDto> getByCity(String city);

    List<TourDto> getFeatured();

    List<TourDto> getVerified();

    List<TourDto> getByH3Index(String h3Index);

    void delete(Long id);
}