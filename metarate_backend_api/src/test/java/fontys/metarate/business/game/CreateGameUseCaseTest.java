package fontys.metarate.business.game;

import fontys.metarate.business.impl.game.CreateGameUseCaseImpl;
import fontys.metarate.domain.game.CreateGameRequest;
import fontys.metarate.domain.game.CreateGameResponse;
import fontys.metarate.domain.genre.Genre;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.entity.GameEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGameUseCaseTest {
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private CreateGameUseCaseImpl createGameUseCase;

    @Test
     void testCreateGame() {
        // arrange
        CreateGameRequest request = new CreateGameRequest();
        request.setTitle("Test Game");
        request.setDeveloper("Test Developer");
        request.setGenreId(Genre.builder().id(1L).name("Test Genre").build().getId());
        request.setReleaseYear(2022);
        request.setDescription("Test Description");
        request.setImageUrl("Test Image URL");
        request.setTrailerUrl("Test Trailer URL");

        when(gameRepository.save(any(GameEntity.class))).thenAnswer(invocation -> {
            GameEntity savedGame = invocation.getArgument(0);
            savedGame.setId(1L);
            return savedGame;
        });

        // act
        CreateGameResponse response = createGameUseCase.createGame(request);

        // assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(gameRepository, times(1)).save(any(GameEntity.class));
    }
}
