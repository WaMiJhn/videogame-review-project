package fontys.metarate.business;

import fontys.metarate.domain.review.CreateReviewRequest;
import fontys.metarate.domain.review.CreateReviewResponse;

public interface CreateReviewUseCase {
    CreateReviewResponse createReview(CreateReviewRequest request);
}
