package fontys.metarate.business.game;

import fontys.metarate.business.exception.GameNotFoundException;
import fontys.metarate.business.impl.game.DeleteGameUseCaseImpl;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.entity.GameEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteGameUseCaseTest {
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    DeleteGameUseCaseImpl deleteGameUseCase;

    @Test
    void deleteGame_validId_gameDeleted() {
        // Arrange
        long gameId = 1L;
        GameEntity game = new GameEntity();
        game.setId(gameId);
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

        // Act
        deleteGameUseCase.deleteGame(gameId);

        // Assert
        verify(gameRepository, times(1)).deleteById(gameId);
        verify(gameRepository, times(1)).findById(gameId);
    }


    @Test
    void deleteGame_gameNotFound_throwsGameNotFoundException() {
        // Arrange
        long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act and Assert
        GameNotFoundException exception = assertThrows(GameNotFoundException.class,
                () -> deleteGameUseCase.deleteGame(gameId));
    }
}
