package fontys.metarate.domain.game;

import fontys.metarate.domain.genre.Genre;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Game {
    private Long id;
    private String title;
    private String developer;
    private Genre genre;
    private int releaseYear;
    private String description;
    private String imageUrl;
    private String trailerUrl;
}
