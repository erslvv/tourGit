package kz.safetrip.safetrip.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.safetrip.safetrip.mapper.TourMapper;
import kz.safetrip.safetrip.model.dto.TourDto;
import kz.safetrip.safetrip.model.entity.Tour;
import kz.safetrip.safetrip.repository.TourBookingRepository;
import kz.safetrip.safetrip.repository.TourRepository;
import kz.safetrip.safetrip.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourBookingRepository tourBookingRepository;
    private final TourMapper tourMapper;

    @Override
    @Transactional
    public TourDto create(TourDto dto) {
        validateCapacity(dto);
        validateCoordinates(dto.getStartLat(), dto.getStartLng());
        Tour entity = tourMapper.toEntity(dto);
        entity.setId(null);
        Tour saved = tourRepository.save(entity);
        return enrichDto(tourMapper.toDto(saved));
    }

    @Override
    @Transactional
    public TourDto update(Long id, TourDto dto) {
        validateCapacity(dto);
        validateCoordinates(dto.getStartLat(), dto.getStartLng());
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
        existing.setInstagramUrl(dto.getInstagramUrl());
        existing.setStartDate(dto.getStartDate());
        existing.setStartTime(dto.getStartTime());
        existing.setCapacity(dto.getCapacity());
        existing.setH3Index(dto.getH3Index());

        return enrichDto(tourMapper.toDto(tourRepository.save(existing)));
    }

    @Override
    public TourDto getById(Long id) {
        return enrichDto(tourMapper.toDto(getEntityById(id)));
    }

    @Override
    public List<TourDto> getAll() {
        return tourMapper.toDtoList(tourRepository.findAll())
                .stream()
                .map(this::enrichDto)
                .toList();
    }

    @Override
    public List<TourDto> getByCity(String city) {
        return tourMapper.toDtoList(tourRepository.findAllByCityIgnoreCase(city))
                .stream()
                .map(this::enrichDto)
                .toList();
    }

    @Override
    public List<TourDto> getFeatured() {
        return tourMapper.toDtoList(tourRepository.findAllByIsFeaturedTrue())
                .stream()
                .map(this::enrichDto)
                .toList();
    }

    @Override
    public List<TourDto> getVerified() {
        return tourMapper.toDtoList(tourRepository.findAllByIsVerifiedTrue())
                .stream()
                .map(this::enrichDto)
                .toList();
    }

    @Override
    public List<TourDto> getByH3Index(String h3Index) {
        return tourMapper.toDtoList(tourRepository.findAllByH3Index(h3Index))
                .stream()
                .map(this::enrichDto)
                .toList();
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

    private TourDto enrichDto(TourDto dto) {
        long bookedSeats = dto.getId() == null ? 0 : tourBookingRepository.countByTourId(dto.getId());
        dto.setBookedSeats((int) bookedSeats);
        dto.setRemainingSeats(dto.getCapacity() == null ? null : Math.max(dto.getCapacity() - (int) bookedSeats, 0));
        return dto;
    }

    private void validateCapacity(TourDto dto) {
        if (dto.getCapacity() != null && dto.getCapacity() < 1) {
            throw new IllegalArgumentException("Tour capacity must be at least 1.");
        }
    }

    private void validateCoordinates(BigDecimal latitude, BigDecimal longitude) {
        if (latitude != null && (latitude.compareTo(BigDecimal.valueOf(-90)) < 0 || latitude.compareTo(BigDecimal.valueOf(90)) > 0)) {
            throw new IllegalArgumentException("Start latitude must be between -90 and 90.");
        }
        if (longitude != null && (longitude.compareTo(BigDecimal.valueOf(-180)) < 0 || longitude.compareTo(BigDecimal.valueOf(180)) > 0)) {
            throw new IllegalArgumentException("Start longitude must be between -180 and 180.");
        }
    }
}
