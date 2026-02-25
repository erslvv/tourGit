package kz.safetrip.safetrip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteTourDto {
    private Long id;
    private Long userId;
    private Long tourId;
    private LocalDateTime createdAt;
}
