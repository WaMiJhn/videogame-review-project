package fontys.metarate.business.review;

import fontys.metarate.business.impl.review.GetCountReviewsByUserUseCaseImpl;
import fontys.metarate.persistence.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCountReviewsByUserUseCaseTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private GetCountReviewsByUserUseCaseImpl getCountReviewsByUserUseCase;

    @Test
    void getCountReviewsByUser_shouldReturnCountOfReviewsForUser() {
        // Arrange
        String username = "testUser";
        int expectedReviewCount = 3;

        when(reviewRepository.countByUserUsername(username)).thenReturn((long) expectedReviewCount);

        // Act
        Long result = getCountReviewsByUserUseCase.getCountReviewsByUser(username);

        // Assert
        assertEquals(expectedReviewCount, result);
        verify(reviewRepository, times(1)).countByUserUsername(username);
    }

    @Test
    void getCountReviewsByUser_shouldReturnZeroWhenNoReviewsForUser() {
        // Arrange
        String username = "testUser";
        int expectedReviewCount = 0;

        when(reviewRepository.countByUserUsername(username)).thenReturn((long) expectedReviewCount);

        // Act
        Long result = getCountReviewsByUserUseCase.getCountReviewsByUser(username);

        // Assert
        assertEquals(expectedReviewCount, result);
        verify(reviewRepository, times(1)).countByUserUsername(username);
    }
}
