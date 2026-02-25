package kz.safetrip.safetrip.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.safetrip.safetrip.mapper.AreaPopularityDailyMapper;
import kz.safetrip.safetrip.model.dto.AreaPopularityDailyDto;
import kz.safetrip.safetrip.model.entity.AreaPopularityDaily;
import kz.safetrip.safetrip.repository.AreaPopularityDailyRepository;
import kz.safetrip.safetrip.service.AreaPopularityDailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AreaPopularityDailyServiceImpl implements AreaPopularityDailyService {

    private final AreaPopularityDailyRepository areaPopularityDailyRepository;
    private final AreaPopularityDailyMapper areaPopularityDailyMapper;

    @Override
    @Transactional
    public AreaPopularityDailyDto upsert(AreaPopularityDailyDto dto) {
        if (dto.getStatDate() == null || dto.getH3Index() == null || dto.getH3Index().isBlank()) {
            throw new IllegalArgumentException("statDate and h3Index are required");
        }

        AreaPopularityDaily entity = areaPopularityDailyRepository
                .findByStatDateAndH3Index(dto.getStatDate(), dto.getH3Index())
                .orElseGet(() -> {
                    AreaPopularityDaily created = new AreaPopularityDaily();
                    created.setStatDate(dto.getStatDate());
                    created.setH3Index(dto.getH3Index());
                    return created;
                });

        entity.setFavoritesCount(dto.getFavoritesCount());
        entity.setPlacesCountSnapshot(dto.getPlacesCountSnapshot());

        AreaPopularityDaily saved = areaPopularityDailyRepository.save(entity);
        return areaPopularityDailyMapper.toDto(saved);
    }

    @Override
    public AreaPopularityDailyDto getById(Long id) {
        return areaPopularityDailyMapper.toDto(getEntityById(id));
    }

    @Override
    public List<AreaPopularityDailyDto> getAll() {
        return areaPopularityDailyMapper.toDtoList(areaPopularityDailyRepository.findAll());
    }

    @Override
    public List<AreaPopularityDailyDto> getByStatDate(LocalDate statDate) {
        return areaPopularityDailyMapper.toDtoList(areaPopularityDailyRepository.findAllByStatDate(statDate));
    }

    @Override
    public List<AreaPopularityDailyDto> getByH3Index(String h3Index) {
        return areaPopularityDailyMapper.toDtoList(areaPopularityDailyRepository.findAllByH3Index(h3Index));
    }

    @Override
    public AreaPopularityDailyDto getByStatDateAndH3Index(LocalDate statDate, String h3Index) {
        AreaPopularityDaily entity = areaPopularityDailyRepository.findByStatDateAndH3Index(statDate, h3Index)
                .orElseThrow(() -> new EntityNotFoundException(
                        "AreaPopularityDaily not found for statDate=" + statDate + ", h3Index=" + h3Index
                ));
        return areaPopularityDailyMapper.toDto(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!areaPopularityDailyRepository.existsById(id)) {
            throw new EntityNotFoundException("AreaPopularityDaily not found: " + id);
        }
        areaPopularityDailyRepository.deleteById(id);
    }

    private AreaPopularityDaily getEntityById(Long id) {
        return areaPopularityDailyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AreaPopularityDaily not found: " + id));
    }
}