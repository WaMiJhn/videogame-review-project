package fontys.metarate.business.review;

import fontys.metarate.business.exception.ReviewNotFoundException;
import fontys.metarate.business.exception.UnauthorizedDataAccessException;
import fontys.metarate.business.impl.review.DeleteReviewUseCaseImpl;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.persistence.ReviewRepository;
import fontys.metarate.persistence.entity.ReviewEntity;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteReviewUseCaseTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private AccessToken requestAccessToken;

    @InjectMocks
    private DeleteReviewUseCaseImpl deleteReviewUseCase;

    @Test
    void deleteReview_shouldDeleteReviewWhenUserOwnsReview() {
        // Arrange
        Long reviewId = 1L;
        Long userId = 123L;

        // Create a review with the user matching the request access token
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .id(reviewId)
                .user(UserEntity.builder().id(userId).build())
                .build();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(reviewEntity));
        when(requestAccessToken.getUserId()).thenReturn(userId);

        // Act
        deleteReviewUseCase.deleteReview(reviewId);

        // Assert
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    void deleteReview_shouldThrowReviewNotFoundExceptionWhenReviewNotFound() {
        // Arrange
        Long reviewId = 1L;

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ReviewNotFoundException.class, () -> deleteReviewUseCase.deleteReview(reviewId));
        verify(reviewRepository, never()).deleteById(any());
    }

    @Test
    void deleteReview_shouldThrowUnauthorizedDataAccessExceptionWhenUserDoesNotOwnReview() {
        // Arrange
        Long reviewId = 1L;
        Long userId = 123L;
        Long anotherUserId = 456L;

        // Create a review with a different user than the request access token
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .id(reviewId)
                .user(UserEntity.builder().id(anotherUserId).build())
                .build();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(reviewEntity));
        when(requestAccessToken.getUserId()).thenReturn(userId);

        // Act and Assert
        assertThrows(UnauthorizedDataAccessException.class, () -> deleteReviewUseCase.deleteReview(reviewId));
        verify(reviewRepository, never()).deleteById(any());
    }
}

