package fontys.metarate.business.review;

import fontys.metarate.business.impl.review.GetCountReviewsByGameUseCaseImpl;
import fontys.metarate.persistence.ReviewRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.ReviewEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCountReviewsByGameUseCaseTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private GetCountReviewsByGameUseCaseImpl getCountReviewsByGameUseCase;

    @Test
    void getCountReviewsByGameId_shouldReturnCountOfReviewsForGame() {
        // Arrange
        Long gameId = 1L;
        int expectedReviewCount = 2;

        // Create two reviews for the same game
        List<ReviewEntity> reviews = List.of(
                ReviewEntity.builder().id(1L).game(GameEntity.builder().id(gameId).build()).build(),
                ReviewEntity.builder().id(2L).game(GameEntity.builder().id(gameId).build()).build(),
                ReviewEntity.builder().id(3L).game(GameEntity.builder().id(2L).build()).build()
        );

        when(reviewRepository.countByGameId(gameId)).thenReturn((long) expectedReviewCount);

        // Act
        Long result = getCountReviewsByGameUseCase.getCountReviewsByGameId(gameId);

        // Assert
        assertEquals(expectedReviewCount, result);
        verify(reviewRepository, times(1)).countByGameId(gameId);
    }

    @Test
    void getCountReviewsByGameId_shouldReturnZeroWhenNoReviewsForGame() {
        // Arrange
        Long gameId = 1L;
        int expectedReviewCount = 0;

        when(reviewRepository.countByGameId(gameId)).thenReturn((long) expectedReviewCount);

        // Act
        Long result = getCountReviewsByGameUseCase.getCountReviewsByGameId(gameId);

        // Assert
        assertEquals(expectedReviewCount, result);
        verify(reviewRepository, times(1)).countByGameId(gameId);
    }
}

