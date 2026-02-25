package kz.safetrip.safetrip.repository.jpa;

import kz.safetrip.safetrip.model.entity.AreaPopularityDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface AreaPopularityDailyJpaRepository extends JpaRepository<AreaPopularityDaily, Long> {
    Optional<AreaPopularityDaily> findByStatDateAndH3Index(LocalDate statDate, String h3Index);

    boolean existsByStatDateAndH3Index(LocalDate statDate, String h3Index);

    List<AreaPopularityDaily> findByStatDate(LocalDate statDate);

    List<AreaPopularityDaily> findByH3Index(String h3Index);

    List<AreaPopularityDaily> findByStatDateBetween(LocalDate from, LocalDate to);

    long deleteByStatDate(LocalDate statDate);
}