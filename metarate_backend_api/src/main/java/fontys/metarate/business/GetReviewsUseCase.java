package fontys.metarate.business;

import fontys.metarate.domain.review.GetAllReviewsRequest;
import fontys.metarate.domain.review.GetAllReviewsResponse;

public interface GetReviewsUseCase {
    public GetAllReviewsResponse getReviews(final GetAllReviewsRequest request);
}
