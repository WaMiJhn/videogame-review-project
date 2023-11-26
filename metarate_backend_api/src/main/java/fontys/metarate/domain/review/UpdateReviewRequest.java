package fontys.metarate.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewRequest {
    private Long id;
    @NotNull
    private double rating;
    @NotBlank
    private String content;
}
