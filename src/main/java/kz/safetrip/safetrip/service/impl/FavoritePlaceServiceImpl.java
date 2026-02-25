package kz.safetrip.safetrip.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.safetrip.safetrip.mapper.FavoritePlaceMapper;
import kz.safetrip.safetrip.model.dto.FavoritePlaceDto;
import kz.safetrip.safetrip.model.entity.FavoritePlace;
import kz.safetrip.safetrip.repository.FavoritePlaceRepository;
import kz.safetrip.safetrip.repository.PlaceRepository;
import kz.safetrip.safetrip.repository.UserRepository;
import kz.safetrip.safetrip.service.FavoritePlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoritePlaceServiceImpl implements FavoritePlaceService {

    private final FavoritePlaceRepository favoritePlaceRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final FavoritePlaceMapper favoritePlaceMapper;

    @Override
    @Transactional
    public FavoritePlaceDto create(FavoritePlaceDto dto) {
        if (dto.getUserId() == null || dto.getPlaceId() == null) {
            throw new IllegalArgumentException("userId and placeId are required");
        }

        userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getUserId()));
        placeRepository.findById(dto.getPlaceId())
                .orElseThrow(() -> new EntityNotFoundException("Place not found: " + dto.getPlaceId()));

        return favoritePlaceRepository.findByUserIdAndPlaceId(dto.getUserId(), dto.getPlaceId())
                .map(favoritePlaceMapper::toDto)
                .orElseGet(() -> {
                    FavoritePlace entity = favoritePlaceMapper.toEntity(dto);
                    entity.setId(null);
                    FavoritePlace saved = favoritePlaceRepository.save(entity);
                    return favoritePlaceMapper.toDto(saved);
                });
    }

    @Override
    public FavoritePlaceDto getById(Long id) {
        return favoritePlaceMapper.toDto(getEntityById(id));
    }

    @Override
    public List<FavoritePlaceDto> getAll() {
        return favoritePlaceMapper.toDtoList(favoritePlaceRepository.findAll());
    }

    @Override
    public List<FavoritePlaceDto> getByUserId(Long userId) {
        return favoritePlaceMapper.toDtoList(favoritePlaceRepository.findAllByUser_Id(userId));
    }

    @Override
    public List<FavoritePlaceDto> getByPlaceId(Long placeId) {
        return favoritePlaceMapper.toDtoList(favoritePlaceRepository.findAllByPlace_Id(placeId));
    }

    @Override
    public boolean existsByUserIdAndPlaceId(Long userId, Long placeId) {
        return favoritePlaceRepository.existsByUser_IdAndPlace_Id(userId, placeId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!favoritePlaceRepository.existsById(id)) {
            throw new EntityNotFoundException("FavoritePlace not found: " + id);
        }
        favoritePlaceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByUserIdAndPlaceId(Long userId, Long placeId) {
        favoritePlaceRepository.deleteByUser_IdAndPlace_Id(userId, placeId);
    }

    private FavoritePlace getEntityById(Long id) {
        return favoritePlaceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FavoritePlace not found: " + id));
    }
}