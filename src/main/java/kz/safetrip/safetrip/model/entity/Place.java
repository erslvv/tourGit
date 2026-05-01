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
        name = "places",
        indexes = {
                @Index(name = "idx_places_city", columnList = "city"),
                @Index(name = "idx_places_category", columnList = "category"),
                @Index(name = "idx_places_h3_index", columnList = "h3_index"),
                @Index(name = "idx_places_featured", columnList = "is_featured"),
                @Index(name = "idx_places_verified", columnList = "is_verified")
        }
)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "average_price", precision = 12, scale = 2)
    private BigDecimal averagePrice;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "two_gis_url", columnDefinition = "TEXT")
    private String twoGisUrl;

    @Column(name = "h3_index", length = 32)
    private String h3Index;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
