package fontys.metarate.business.game;

import fontys.metarate.business.impl.game.GetAverageGameRatingUseCaseImpl;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import fontys.metarate.persistence.entity.ReviewEntity;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAverageGameRatingUseCaseTest {
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GetAverageGameRatingUseCaseImpl getAverageGameRatingUseCase;

    @Test
    void getAverageRatingByGameId_shouldReturnAverageRating() {
        // Arrange
        Long gameId = 1L;
        double expectedAverageRating = 8.5;

        GameEntity gameEntity = GameEntity.builder()
                .id(gameId)
                .title("Game 1")
                .developer("Developer 1")
                .genre(GenreEntity.builder().name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build();

        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(ReviewEntity.builder()
                .id(1L)
                .rating(8.0)
                .date(LocalDateTime.now())
                .content("Review 1")
                .user(UserEntity.builder().username("User 1").build())
                .game(gameEntity)
                .build());
        reviewEntities.add(ReviewEntity.builder()
                .id(2L)
                .rating(9.0)
                .date(LocalDateTime.now())
                .content("Review 2")
                .user(UserEntity.builder().username("User 2").build())
                .game(gameEntity)
                .build());

        when(gameRepository.getAverageRatingByGameId(gameId)).thenReturn(expectedAverageRating);

        // Act
        Double averageRating = getAverageGameRatingUseCase.getAverageRatingByGameId(gameId);

        // Assert
        assertEquals(expectedAverageRating, averageRating, 0.01);

        verify(gameRepository, times(1)).getAverageRatingByGameId(gameId);
    }
}
