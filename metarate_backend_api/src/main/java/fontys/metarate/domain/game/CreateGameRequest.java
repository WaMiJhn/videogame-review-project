package fontys.metarate.domain.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGameRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String developer;
    @NotNull
    private Long genreId;
    @NotNull
    private int releaseYear;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String trailerUrl;
}
