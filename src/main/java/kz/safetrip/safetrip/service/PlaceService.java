package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.model.dto.PlaceDto;

import java.util.List;

public interface PlaceService {

    PlaceDto create(PlaceDto dto);

    PlaceDto update(Long id, PlaceDto dto);

    PlaceDto getById(Long id);

    List<PlaceDto> getAll();

    List<PlaceDto> getByCity(String city);

    List<PlaceDto> getByCategory(String category);

    List<PlaceDto> getFeatured();

    List<PlaceDto> getVerified();

    List<PlaceDto> getByH3Index(String h3Index);

    void delete(Long id);
}