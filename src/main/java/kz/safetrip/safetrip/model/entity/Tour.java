package kz.safetrip.safetrip.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "tours",
        indexes = {
                @Index(name = "idx_tours_city", columnList = "city"),
                @Index(name = "idx_tours_h3_index", columnList = "h3_index"),
                @Index(name = "idx_tours_featured", columnList = "is_featured"),
                @Index(name = "idx_tours_verified", columnList = "is_verified")
        }
)
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "start_lat", precision = 9, scale = 6)
    private BigDecimal startLat;

    @Column(name = "start_lng", precision = 9, scale = 6)
    private BigDecimal startLng;

    @Column(name = "h3_index", length = 32)
    private String h3Index; // optional for tour start point

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
