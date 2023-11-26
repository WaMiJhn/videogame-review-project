package fontys.metarate.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "game")

public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 1, max = 255)
    @Column(name = "title")
    private String title;

    @NotBlank
    @Length(min = 1, max = 255)
    @Column(name = "developer")
    private String developer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    @NotNull
    @Column(name = "release_year")
    private int releaseYear;

    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    @NotBlank
    @Pattern(regexp = "^https?:\\/\\/(?:www\\.)?youtube\\.com\\/embed\\/[\\w-]+$")
    @Column(name = "trailer_url")
    private String trailerUrl;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ReviewEntity> reviews;
}
