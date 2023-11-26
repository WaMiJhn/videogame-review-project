package fontys.metarate.business.review;

import fontys.metarate.business.exception.ReviewNotFoundException;
import fontys.metarate.business.exception.UnauthorizedDataAccessException;
import fontys.metarate.business.impl.review.UpdateReviewUseCaseImpl;
import fontys.metarate.domain.AccessToken;
import fontys.metarate.domain.review.UpdateReviewRequest;
import fontys.metarate.persistence.ReviewRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.ReviewEntity;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateReviewUseCaseTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private AccessToken requestAccessToken;

    @InjectMocks
    private UpdateReviewUseCaseImpl updateReviewUseCase;

    @Test
    void updateReview_shouldUpdateReviewFields() {
        // Arrange
        Long reviewId = 1L;
        double newRating = 9.5;
        String newContent = "Updated review content";

        UpdateReviewRequest request = new UpdateReviewRequest();
        request.setId(reviewId);
        request.setRating(newRating);
        request.setContent(newContent);

        ReviewEntity existingReview = createReviewEntity(reviewId);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(requestAccessToken.getUserId()).thenReturn(existingReview.getUser().getId());

        // Act
        updateReviewUseCase.updateReview(request);

        // Assert
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(requestAccessToken, times(1)).getUserId();
        verify(reviewRepository, times(1)).save(existingReview);
        assertEquals(newRating, existingReview.getRating());
        assertEquals(newContent, existingReview.getContent());
        assertNotNull(existingReview.getDate());
    }

    @Test
    void updateReview_shouldThrowReviewNotFoundExceptionWhenReviewNotFound() {
        // Arrange
        Long reviewId = 1L;

        UpdateReviewRequest request = new UpdateReviewRequest();
        request.setId(reviewId);
        request.setRating(9.5);
        request.setContent("Updated review content");

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ReviewNotFoundException.class, () -> updateReviewUseCase.updateReview(request));
        verify(reviewRepository, times(1)).findById(reviewId);
        verifyNoInteractions(requestAccessToken);
        verifyNoMoreInteractions(reviewRepository);
    }

    @Test
    void updateReview_shouldThrowUnauthorizedDataAccessExceptionWhenReviewNotOwnedByUser() {
        // Arrange
        Long reviewId = 1L;
        Long loggedInUserId = 1L;
        // The review owner ID is 2L

        UpdateReviewRequest request = new UpdateReviewRequest();
        request.setId(reviewId);
        request.setRating(9.5);
        request.setContent("Updated review content");

        ReviewEntity existingReview = createReviewEntity(reviewId); // Pass the review owner ID
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(requestAccessToken.getUserId()).thenReturn(loggedInUserId);

        // Act & Assert
        assertThrows(UnauthorizedDataAccessException.class, () -> updateReviewUseCase.updateReview(request));
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(requestAccessToken, times(1)).getUserId();
        verifyNoMoreInteractions(reviewRepository);
    }

    private ReviewEntity createReviewEntity(Long reviewId) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(reviewId);
        reviewEntity.setRating(8.0);
        reviewEntity.setContent("Original review content");
        reviewEntity.setDate(LocalDateTime.now());
        reviewEntity.setUser(UserEntity.builder().id(2L).build());
        reviewEntity.setGame(new GameEntity());
        return reviewEntity;
    }
}


