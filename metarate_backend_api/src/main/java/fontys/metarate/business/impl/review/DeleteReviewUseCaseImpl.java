package fontys.metarate.business.impl.review;

import fontys.metarate.business.DeleteReviewUseCase;
import fontys.metarate.business.exception.ReviewNotFoundException;
import fontys.metarate.business.exception.UnauthorizedDataAccessException;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.persistence.ReviewRepository;
import fontys.metarate.persistence.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteReviewUseCaseImpl implements DeleteReviewUseCase {
    private ReviewRepository reviewRepository;
    private final AccessToken requestAccessToken;

    @Override
    public void deleteReview(Long reviewId) {
        Optional<ReviewEntity> reviewOptional = reviewRepository.findById(reviewId);
        ReviewEntity review = reviewOptional.orElseThrow(ReviewNotFoundException::new);
        if (!requestAccessToken.getUserId().equals(review.getUser().getId())) {
            throw new UnauthorizedDataAccessException("REVIEW_NOT_OWNED_BY_USER");
        }

        reviewRepository.deleteById(reviewId);
    }
}
