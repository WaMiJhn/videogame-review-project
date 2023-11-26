package fontys.metarate.business.review;

import fontys.metarate.business.impl.review.CreateReviewUseCaseImpl;
import fontys.metarate.domain.review.CreateReviewRequest;
import fontys.metarate.domain.review.CreateReviewResponse;
import fontys.metarate.persistence.ReviewRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.ReviewEntity;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateReviewUseCaseTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private CreateReviewUseCaseImpl createReviewUseCase;

    @Test
    void createReview_shouldCreateReviewAndReturnId() {
        // Arrange
        long reviewId = 1L;
        int rating = 4;
        String content = "Great game!";
        LocalDateTime date = LocalDateTime.now();
        long userId = 1L;
        long gameId = 1L;

        CreateReviewRequest request = CreateReviewRequest.builder()
                .rating(rating)
                .content(content)
                .userId(userId)
                .gameId(gameId)
                .build();

        ReviewEntity savedReview = ReviewEntity.builder()
                .id(reviewId)
                .rating(rating)
                .content(content)
                .date(date)
                .user(UserEntity.builder().id(userId).build())
                .game(GameEntity.builder().id(gameId).build())
                .build();

        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(savedReview);

        // Act
        CreateReviewResponse response = createReviewUseCase.createReview(request);

        // Assert
        assertEquals(reviewId, response.getId());
        verify(reviewRepository).save(any(ReviewEntity.class));
    }
}

