package kz.safetrip.safetrip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaPopularityDailyDto {
    private Long id;
    private LocalDate statDate;
    private String h3Index;
    private Integer favoritesCount;
    private Integer placesCountSnapshot;
    private LocalDateTime updatedAt;
}
