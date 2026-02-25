package kz.safetrip.safetrip.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "area_popularity_daily",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_area_popularity_daily_date_h3", columnNames = {"stat_date", "h3_index"})
        },
        indexes = {
                @Index(name = "idx_area_popularity_daily_stat_date", columnList = "stat_date"),
                @Index(name = "idx_area_popularity_daily_h3_index", columnList = "h3_index")
        }
)
public class AreaPopularityDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stat_date", nullable = false)
    private LocalDate statDate;

    @Column(name = "h3_index", nullable = false, length = 32)
    private String h3Index;

    @Column(name = "favorites_count", nullable = false)
    private Integer favoritesCount;

    @Column(name = "places_count_snapshot")
    private Integer placesCountSnapshot;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
