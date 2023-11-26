package fontys.metarate.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllReviewsRequest {
    private String username;
    private Long gameId;
    private int page;
    private int size;
}
