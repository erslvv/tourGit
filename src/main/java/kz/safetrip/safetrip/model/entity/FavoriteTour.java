package kz.safetrip.safetrip.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "favorite_tours",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_favorite_tours_user_tour", columnNames = {"user_id", "tour_id"})
        },
        indexes = {
                @Index(name = "idx_favorite_tours_user_id", columnList = "user_id"),
                @Index(name = "idx_favorite_tours_tour_id", columnList = "tour_id")
        }
)
public class FavoriteTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_tours_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_tours_tour"))
    private Tour tour;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
