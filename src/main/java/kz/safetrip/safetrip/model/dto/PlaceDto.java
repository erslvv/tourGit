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
public class PlaceDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private BigDecimal averagePrice;
    private BigDecimal rating;
    private String imageUrl;
    private Boolean isFeatured;
    private Boolean isVerified;
    private String city;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String twoGisUrl;
    private String h3Index;
    private LocalDateTime createdAt;
}
