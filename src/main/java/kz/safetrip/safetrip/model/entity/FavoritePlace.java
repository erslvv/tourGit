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
        name = "favorite_places",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_favorite_places_user_place", columnNames = {"user_id", "place_id"})
        },
        indexes = {
                @Index(name = "idx_favorite_places_user_id", columnList = "user_id"),
                @Index(name = "idx_favorite_places_place_id", columnList = "place_id")
        }
)
public class FavoritePlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_places_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_places_place"))
    private Place place;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
