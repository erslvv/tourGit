package kz.safetrip.safetrip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private String instagramUrl;
    private LocalDate startDate;
    private LocalTime startTime;
    private Integer capacity;
    private Integer bookedSeats;
    private Integer remainingSeats;
    private String h3Index;
    private LocalDateTime createdAt;
}
