package fontys.metarate.business.game;

import fontys.metarate.business.exception.GameNotFoundException;
import fontys.metarate.business.impl.game.UpdateGameUseCaseImpl;
import fontys.metarate.domain.game.UpdateGameRequest;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateGameUseCaseTest {
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private UpdateGameUseCaseImpl updateGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateGame_shouldUpdateGameIfFound() {
        // Arrange
        long gameId = 1L;
        UpdateGameRequest request = UpdateGameRequest.builder()
                .id(gameId)
                .title("Updated Game")
                .developer("Updated Developer")
                .genreId(2L)
                .rating(4.8)
                .releaseYear(2022)
                .description("Updated Description")
                .imageUrl("https://example.com/updated-image.jpg")
                .trailerUrl("https://example.com/updated-trailer.mp4")
                .build();

        GameEntity existingGame = GameEntity.builder()
                .id(gameId)
                .title("Game 1")
                .developer("Developer 1")
                .genre(GenreEntity.builder().id(1L).build())
                .releaseYear(2021)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://example.com/trailer1.mp4")
                .build();

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(existingGame));

        // Act
        updateGameUseCase.updateGame(request);

        // Assert
        verify(gameRepository).findById(gameId);
        verify(gameRepository).save(existingGame);

        assertEquals(request.getTitle(), existingGame.getTitle());
        assertEquals(request.getDeveloper(), existingGame.getDeveloper());
        assertEquals(request.getGenreId(), existingGame.getGenre().getId());
        assertEquals(request.getReleaseYear(), existingGame.getReleaseYear());
        assertEquals(request.getDescription(), existingGame.getDescription());
        assertEquals(request.getImageUrl(), existingGame.getImageUrl());
        assertEquals(request.getTrailerUrl(), existingGame.getTrailerUrl());
    }

    @Test
    void updateGame_shouldThrowGameNotFoundExceptionIfGameNotFound() {
        // Arrange
        long gameId = 1L;
        UpdateGameRequest request = UpdateGameRequest.builder()
                .id(gameId)
                .title("Updated Game")
                .developer("Updated Developer")
                .genreId(2L)
                .rating(4.8)
                .releaseYear(2022)
                .description("Updated Description")
                .imageUrl("https://example.com/updated-image.jpg")
                .trailerUrl("https://example.com/updated-trailer.mp4")
                .build();

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(GameNotFoundException.class, () -> updateGameUseCase.updateGame(request));

        verify(gameRepository, times(1)).findById(gameId);
        verify(gameRepository, never()).save(any());
    }
}
