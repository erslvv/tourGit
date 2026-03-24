package kz.safetrip.safetrip.repository.impl;

import kz.safetrip.safetrip.model.entity.AreaPopularityDaily;
import kz.safetrip.safetrip.repository.AreaPopularityDailyRepository;
import kz.safetrip.safetrip.repository.jpa.AreaPopularityDailyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AreaPopularityDailyRepositoryImpl implements AreaPopularityDailyRepository {
    private final AreaPopularityDailyJpaRepository areaPopularityDailyJpaRepository;
    @Override @Transactional public AreaPopularityDaily save(AreaPopularityDaily entity) { return areaPopularityDailyJpaRepository.save(entity); }
    @Override @Transactional public List<AreaPopularityDaily> saveAll(List<AreaPopularityDaily> entities) { return areaPopularityDailyJpaRepository.saveAll(entities); }
    @Override public Optional<AreaPopularityDaily> findById(Long id) { return areaPopularityDailyJpaRepository.findById(id); }
    @Override public boolean existsByStatDateAndH3Index(LocalDate statDate, String h3Index) { return areaPopularityDailyJpaRepository.existsByStatDateAndH3Index(statDate, h3Index); }
    @Override public List<AreaPopularityDaily> findByStatDate(LocalDate statDate) { return areaPopularityDailyJpaRepository.findByStatDate(statDate); }
    @Override public List<AreaPopularityDaily> findByH3Index(String h3Index) { return areaPopularityDailyJpaRepository.findByH3Index(h3Index); }
    @Override public List<AreaPopularityDaily> findByStatDateBetween(LocalDate from, LocalDate to) { return areaPopularityDailyJpaRepository.findByStatDateBetween(from, to); }
    @Override @Transactional public void deleteById(Long id) { areaPopularityDailyJpaRepository.deleteById(id); }
    @Override @Transactional public long deleteByStatDate(LocalDate statDate) { return areaPopularityDailyJpaRepository.deleteByStatDate(statDate); }
    @Override public boolean existsById(Long id) { return areaPopularityDailyJpaRepository.existsById(id); }
    @Override public List<AreaPopularityDaily> findAll() { return areaPopularityDailyJpaRepository.findAll(); }
    @Override public Optional<AreaPopularityDaily> findByStatDateAndH3Index(LocalDate statDate, String h3Index) { return areaPopularityDailyJpaRepository.findByStatDateAndH3Index(statDate, h3Index); }
    @Override public List<AreaPopularityDaily> findAllByStatDate(LocalDate statDate) { return areaPopularityDailyJpaRepository.findByStatDate(statDate); }
    @Override public List<AreaPopularityDaily> findAllByH3Index(String h3Index) { return areaPopularityDailyJpaRepository.findByH3Index(h3Index); }
}
