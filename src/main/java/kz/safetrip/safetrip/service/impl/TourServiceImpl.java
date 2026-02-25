package kz.safetrip.safetrip.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.safetrip.safetrip.mapper.TourMapper;
import kz.safetrip.safetrip.model.dto.TourDto;
import kz.safetrip.safetrip.model.entity.Tour;
import kz.safetrip.safetrip.repository.TourRepository;
import kz.safetrip.safetrip.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    @Override
    @Transactional
    public TourDto create(TourDto dto) {
        Tour entity = tourMapper.toEntity(dto);
        entity.setId(null);
        Tour saved = tourRepository.save(entity);
        return tourMapper.toDto(saved);
    }

    @Override
    @Transactional
    public TourDto update(Long id, TourDto dto) {
        Tour existing = getEntityById(id);

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCity(dto.getCity());
        existing.setDurationDays(dto.getDurationDays());
        existing.setPrice(dto.getPrice());
        existing.setRating(dto.getRating());
        existing.setImageUrl(dto.getImageUrl());
        if (dto.getIsFeatured() != null) {
            existing.setIsFeatured(dto.getIsFeatured());
        }
        if (dto.getIsVerified() != null) {
            existing.setIsVerified(dto.getIsVerified());
        }
        existing.setStartLat(dto.getStartLat());
        existing.setStartLng(dto.getStartLng());
        existing.setH3Index(dto.getH3Index());

        return tourMapper.toDto(tourRepository.save(existing));
    }

    @Override
    public TourDto getById(Long id) {
        return tourMapper.toDto(getEntityById(id));
    }

    @Override
    public List<TourDto> getAll() {
        return tourMapper.toDtoList(tourRepository.findAll());
    }

    @Override
    public List<TourDto> getByCity(String city) {
        return tourMapper.toDtoList(tourRepository.findAllByCityIgnoreCase(city));
    }

    @Override
    public List<TourDto> getFeatured() {
        return tourMapper.toDtoList(tourRepository.findAllByIsFeaturedTrue());
    }

    @Override
    public List<TourDto> getVerified() {
        return tourMapper.toDtoList(tourRepository.findAllByIsVerifiedTrue());
    }

    @Override
    public List<TourDto> getByH3Index(String h3Index) {
        return tourMapper.toDtoList(tourRepository.findAllByH3Index(h3Index));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!tourRepository.existsById(id)) {
            throw new EntityNotFoundException("Tour not found: " + id);
        }
        tourRepository.deleteById(id);
    }

    private Tour getEntityById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tour not found: " + id));
    }
}