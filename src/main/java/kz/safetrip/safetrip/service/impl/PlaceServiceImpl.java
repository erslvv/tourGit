package kz.safetrip.safetrip.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.safetrip.safetrip.mapper.PlaceMapper;
import kz.safetrip.safetrip.model.dto.PlaceDto;
import kz.safetrip.safetrip.model.entity.Place;
import kz.safetrip.safetrip.repository.PlaceRepository;
import kz.safetrip.safetrip.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    @Override
    @Transactional
    public PlaceDto create(PlaceDto dto) {
        Place entity = placeMapper.toEntity(dto);
        entity.setId(null);
        Place saved = placeRepository.save(entity);
        return placeMapper.toDto(saved);
    }

    @Override
    @Transactional
    public PlaceDto update(Long id, PlaceDto dto) {
        Place existing = getEntityById(id);

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCategory(dto.getCategory());
        existing.setAveragePrice(dto.getAveragePrice());
        existing.setRating(dto.getRating());
        existing.setImageUrl(dto.getImageUrl());
        if (dto.getIsFeatured() != null) {
            existing.setIsFeatured(dto.getIsFeatured());
        }
        if (dto.getIsVerified() != null) {
            existing.setIsVerified(dto.getIsVerified());
        }
        existing.setCity(dto.getCity());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());
        existing.setH3Index(dto.getH3Index());

        return placeMapper.toDto(placeRepository.save(existing));
    }

    @Override
    public PlaceDto getById(Long id) {
        return placeMapper.toDto(getEntityById(id));
    }

    @Override
    public List<PlaceDto> getAll() {
        return placeMapper.toDtoList(placeRepository.findAll());
    }

    @Override
    public List<PlaceDto> getByCity(String city) {
        return placeMapper.toDtoList(placeRepository.findAllByCityIgnoreCase(city));
    }

    @Override
    public List<PlaceDto> getByCategory(String category) {
        return placeMapper.toDtoList(placeRepository.findAllByCategoryIgnoreCase(category));
    }

    @Override
    public List<PlaceDto> getFeatured() {
        return placeMapper.toDtoList(placeRepository.findAllByIsFeaturedTrue());
    }

    @Override
    public List<PlaceDto> getVerified() {
        return placeMapper.toDtoList(placeRepository.findAllByIsVerifiedTrue());
    }

    @Override
    public List<PlaceDto> getByH3Index(String h3Index) {
        return placeMapper.toDtoList(placeRepository.findAllByH3Index(h3Index));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!placeRepository.existsById(id)) {
            throw new EntityNotFoundException("Place not found: " + id);
        }
        placeRepository.deleteById(id);
    }

    private Place getEntityById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Place not found: " + id));
    }
}