package kz.safetrip.safetrip.repository;

import kz.safetrip.safetrip.model.entity.AreaPopularityDaily;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AreaPopularityDailyRepository {
    AreaPopularityDaily save(AreaPopularityDaily entity);

    List<AreaPopularityDaily> saveAll(List<AreaPopularityDaily> entities);

    Optional<AreaPopularityDaily> findById(Long id);

    boolean existsByStatDateAndH3Index(LocalDate statDate, String h3Index);

    List<AreaPopularityDaily> findByStatDate(LocalDate statDate);

    List<AreaPopularityDaily> findByH3Index(String h3Index);

    List<AreaPopularityDaily> findByStatDateBetween(LocalDate from, LocalDate to);

    void deleteById(Long id);

    long deleteByStatDate(LocalDate statDate);

    boolean existsById(Long id);

    List<AreaPopularityDaily> findAll();

    Optional<AreaPopularityDaily> findByStatDateAndH3Index(LocalDate statDate, String h3Index);

    List<AreaPopularityDaily> findAllByStatDate(LocalDate statDate);

    List<AreaPopularityDaily> findAllByH3Index(String h3Index);
}