package fontys.metarate.business.game;
import fontys.metarate.business.exception.GameNotFoundException;
import fontys.metarate.business.impl.game.GetGameUseCaseImpl;
import fontys.metarate.domain.game.Game;
import fontys.metarate.domain.genre.Genre;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetGameUseCaseTest {
    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private GetGameUseCaseImpl getGameUseCase;

    @Test
    void getGame_shouldReturnGameIfExists() {
        // Arrange
        long gameId = 1L;
        GameEntity gameEntity = GameEntity.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(GenreEntity.builder().id(1L).name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://example.com/trailer1.mp4")
                .build();
        Game game = Game.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(Genre.builder().id(1L).name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://example.com/trailer1.mp4")
                .build();
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(gameEntity));

        // Act
        Optional<Game> result = getGameUseCase.getGame(gameId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(game, result.get());
        verify(gameRepository, times(1)).findById(gameId);
    }

    @Test
    void getGame_shouldThrowExceptionIfGameNotFound() {
        // Arrange
        long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(GameNotFoundException.class, () -> getGameUseCase.getGame(gameId));
        verify(gameRepository, times(1)).findById(gameId);
    }
}
