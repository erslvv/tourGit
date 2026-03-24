package kz.safetrip.safetrip.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.safetrip.safetrip.mapper.FavoriteTourMapper;
import kz.safetrip.safetrip.model.dto.FavoriteTourDto;
import kz.safetrip.safetrip.model.entity.FavoriteTour;
import kz.safetrip.safetrip.repository.FavoriteTourRepository;
import kz.safetrip.safetrip.repository.TourRepository;
import kz.safetrip.safetrip.repository.UserRepository;
import kz.safetrip.safetrip.service.FavoriteTourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteTourServiceImpl implements FavoriteTourService {
    private final FavoriteTourRepository favoriteTourRepository;
    private final FavoriteTourMapper favoriteTourMapper;
    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    @Override
    @Transactional
    public FavoriteTourDto create(FavoriteTourDto dto) {
        validateLinks(dto.getUserId(), dto.getTourId());
        if (favoriteTourRepository.existsByUser_IdAndTour_Id(dto.getUserId(), dto.getTourId())) throw new IllegalArgumentException("Favorite tour already exists");
        FavoriteTour entity = favoriteTourMapper.toEntity(dto);
        entity.setId(null);
        return favoriteTourMapper.toDto(favoriteTourRepository.save(entity));
    }

    @Override public FavoriteTourDto getById(Long id) { return favoriteTourMapper.toDto(getEntityById(id)); }
    @Override public List<FavoriteTourDto> getAll() { return favoriteTourMapper.toDtoList(favoriteTourRepository.findAll()); }
    @Override public List<FavoriteTourDto> getByUserId(Long userId) { return favoriteTourMapper.toDtoList(favoriteTourRepository.findAllByUser_Id(userId)); }
    @Override public List<FavoriteTourDto> getByTourId(Long tourId) { return favoriteTourMapper.toDtoList(favoriteTourRepository.findAllByTour_Id(tourId)); }
    @Override public boolean existsByUserIdAndTourId(Long userId, Long tourId) { return favoriteTourRepository.existsByUser_IdAndTour_Id(userId, tourId); }
    @Override @Transactional public void deleteById(Long id) { if (!favoriteTourRepository.existsById(id)) throw new EntityNotFoundException("FavoriteTour not found: " + id); favoriteTourRepository.deleteById(id); }
    @Override @Transactional public void deleteByUserIdAndTourId(Long userId, Long tourId) { if (!favoriteTourRepository.deleteByUserIdAndTourId(userId, tourId)) throw new EntityNotFoundException("FavoriteTour not found for userId=" + userId + ", tourId=" + tourId); }

    private FavoriteTour getEntityById(Long id) { return favoriteTourRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("FavoriteTour not found: " + id)); }
    private void validateLinks(Long userId, Long tourId) {
        if (userId == null || tourId == null) throw new IllegalArgumentException("userId and tourId are required");
        if (!userRepository.existsById(userId)) throw new EntityNotFoundException("User not found: " + userId);
        if (!tourRepository.existsById(tourId)) throw new EntityNotFoundException("Tour not found: " + tourId);
    }
}
