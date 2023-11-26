package fontys.metarate.business.impl.review;

import fontys.metarate.business.CreateReviewUseCase;
import fontys.metarate.domain.review.CreateReviewRequest;
import fontys.metarate.domain.review.CreateReviewResponse;
import fontys.metarate.persistence.ReviewRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.ReviewEntity;
import fontys.metarate.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateReviewUseCaseImpl implements CreateReviewUseCase {
    private final ReviewRepository reviewRepository;
    @Override
    public CreateReviewResponse createReview(CreateReviewRequest request) {
        ReviewEntity savedReview = saveNewReview(request);

        return CreateReviewResponse.builder()
                .id(savedReview.getId())
                .build();
    }

    private ReviewEntity saveNewReview(CreateReviewRequest request) {
        ReviewEntity newReview = ReviewEntity.builder()
                .rating(request.getRating())
                .content(request.getContent())
                .date(LocalDateTime.now())
                .user(UserEntity.builder().id(request.getUserId()).build())
                .game(GameEntity.builder().id(request.getGameId()).build())
                .build();
        return reviewRepository.save(newReview);
    }
}
