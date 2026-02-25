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
public class FavoritePlaceDto {
    private Long id;
    private Long userId;
    private Long placeId;
    private LocalDateTime createdAt;
}
