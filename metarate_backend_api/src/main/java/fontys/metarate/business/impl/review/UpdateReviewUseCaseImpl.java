package fontys.metarate.business.impl.review;

import fontys.metarate.business.UpdateReviewUseCase;
import fontys.metarate.business.exception.ReviewNotFoundException;
import fontys.metarate.business.exception.UnauthorizedDataAccessException;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.domain.review.UpdateReviewRequest;
import fontys.metarate.persistence.ReviewRepository;
import fontys.metarate.persistence.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateReviewUseCaseImpl implements UpdateReviewUseCase {
    private final ReviewRepository reviewRepository;
    private final AccessToken requestAccessToken;

    @Override
    public void updateReview(UpdateReviewRequest request) {
        Optional<ReviewEntity> reviewOptional = reviewRepository.findById(request.getId());
        ReviewEntity review = reviewOptional.orElseThrow(ReviewNotFoundException::new);

        // Check if the review belongs to the logged-in user
        if (!requestAccessToken.getUserId().equals(review.getUser().getId())) {
            throw new UnauthorizedDataAccessException("REVIEW_NOT_OWNED_BY_USER");
        }

        updateFields(request, review);
    }

    private void updateFields(UpdateReviewRequest request, ReviewEntity review) {
        review.setRating(request.getRating());
        review.setDate(LocalDateTime.now());
        review.setContent(request.getContent());

        reviewRepository.save(review);
    }
}

