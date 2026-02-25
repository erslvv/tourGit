package kz.safetrip.safetrip.service;

import kz.safetrip.safetrip.model.dto.AreaPopularityDailyDto;

import java.time.LocalDate;
import java.util.List;

public interface AreaPopularityDailyService {

    AreaPopularityDailyDto upsert(AreaPopularityDailyDto dto);

    AreaPopularityDailyDto getById(Long id);

    List<AreaPopularityDailyDto> getAll();

    List<AreaPopularityDailyDto> getByStatDate(LocalDate statDate);

    List<AreaPopularityDailyDto> getByH3Index(String h3Index);

    AreaPopularityDailyDto getByStatDateAndH3Index(LocalDate statDate, String h3Index);

    void delete(Long id);
}