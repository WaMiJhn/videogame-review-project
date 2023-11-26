package fontys.metarate.domain.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllGamesRequest {
    private String genreName;
    private String title;
    private int page;
    private int size;
}
