package fontys.metarate.domain.genre;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetGenresResponse {
    private List<Genre> genres;
}
