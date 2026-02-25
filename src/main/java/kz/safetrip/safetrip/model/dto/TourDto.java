package kz.safetrip.safetrip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourDto {
    private Long id;
    private String title;
    private String description;
    private String city;
    private Integer durationDays;
    private BigDecimal price;
    private BigDecimal rating;
    private String imageUrl;
    private Boolean isFeatured;
    private Boolean isVerified;
    private BigDecimal startLat;
    private BigDecimal startLng;
    private String h3Index;
    private LocalDateTime createdAt;
}
