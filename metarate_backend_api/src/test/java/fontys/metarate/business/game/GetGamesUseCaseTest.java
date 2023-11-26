package fontys.metarate.business.game;

import fontys.metarate.business.impl.game.GetGamesUseCaseImpl;
import fontys.metarate.domain.game.Game;
import fontys.metarate.domain.game.GetAllGamesRequest;
import fontys.metarate.domain.game.GetAllGamesResponse;
import fontys.metarate.domain.genre.Genre;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetGamesUseCaseTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GetGamesUseCaseImpl getGamesUseCase;

    @Test
    void getGames_shouldReturnAllGamesConverted() {
        // Arrange
        GetAllGamesRequest request = new GetAllGamesRequest();
        request.setPage(0);
        request.setSize(10);

        List<GameEntity> gameEntities = new ArrayList<>();
        gameEntities.add(GameEntity.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(GenreEntity.builder().name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());
        gameEntities.add(GameEntity.builder()
                .id(2L)
                .title("Game 2")
                .developer("Developer 2")
                .genre(GenreEntity.builder().name("Genre 2").build())
                .releaseYear(2021)
                .description("Description 2")
                .imageUrl("https://example.com/image2.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());

        List<Game> expectedGames = new ArrayList<>();
        expectedGames.add(Game.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(Genre.builder().name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());
        expectedGames.add(Game.builder()
                .id(2L)
                .title("Game 2")
                .developer("Developer 2")
                .genre(Genre.builder().name("Genre 2").build())
                .releaseYear(2021)
                .description("Description 2")
                .imageUrl("https://example.com/image2.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());

        Page<GameEntity> gameEntityPage = new PageImpl<>(gameEntities);

        when(gameRepository.findAll(any(Pageable.class))).thenReturn(gameEntityPage);

        // Act
        GetAllGamesResponse response = getGamesUseCase.getGames(request);

        // Assert
        assertEquals(expectedGames, response.getGames());

        verify(gameRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getGames_shouldReturnFilteredGamesByGenreAndName() {
        // Arrange
        GetAllGamesRequest request = new GetAllGamesRequest();
        request.setPage(0);
        request.setSize(10);
        request.setGenreName("Genre 1");
        request.setTitle("Game");

        List<GameEntity> filteredEntities = new ArrayList<>();
        filteredEntities.add(GameEntity.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(GenreEntity.builder().name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());

        List<Game> expectedFilteredGames = new ArrayList<>();
        expectedFilteredGames.add(Game.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(Genre.builder().name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());

        Page<GameEntity> filteredEntityPage = new PageImpl<>(filteredEntities);

        when(gameRepository.findAllByTitleContainingIgnoreCaseAndGenreName(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(filteredEntityPage);

        // Act
        GetAllGamesResponse response = getGamesUseCase.getGames(request);

        // Assert
        assertEquals(expectedFilteredGames, response.getGames());

        verify(gameRepository, times(1))
                .findAllByTitleContainingIgnoreCaseAndGenreName(anyString(), anyString(), any(Pageable.class));
        verify(gameRepository, never()).findAll(any(Pageable.class));
    }

    @Test
    void getGames_shouldReturnFilteredGamesByGenre() {
        // Arrange
        GetAllGamesRequest request = new GetAllGamesRequest();
        request.setPage(0);
        request.setSize(10);
        request.setGenreName("Genre 1");

        List<GameEntity> filteredEntities = new ArrayList<>();
        filteredEntities.add(GameEntity.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(GenreEntity.builder().name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());

        List<Game> expectedFilteredGames = new ArrayList<>();
        expectedFilteredGames.add(Game.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(Genre.builder().name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());

        Page<GameEntity> filteredEntityPage = new PageImpl<>(filteredEntities);

        when(gameRepository.findAllByGenreName(anyString(), any(Pageable.class))).thenReturn(filteredEntityPage);

        // Act
        GetAllGamesResponse response = getGamesUseCase.getGames(request);

        // Assert
        assertEquals(expectedFilteredGames, response.getGames());

        verify(gameRepository, times(1)).findAllByGenreName(anyString(), any(Pageable.class));
        verify(gameRepository, never()).findAll(any(Pageable.class));
    }

    @Test
    void getGames_shouldReturnFilteredGamesByName() {
        // Arrange
        GetAllGamesRequest request = new GetAllGamesRequest();
        request.setPage(0);
        request.setSize(10);
        request.setTitle("Game 1");

        List<GameEntity> filteredEntities = new ArrayList<>();
        filteredEntities.add(GameEntity.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(GenreEntity.builder().name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());

        List<Game> expectedFilteredGames = new ArrayList<>();
        expectedFilteredGames.add(Game.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(Genre.builder().name("Genre 1").build())
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build());

        Page<GameEntity> filteredEntityPage = new PageImpl<>(filteredEntities);

        when(gameRepository.findAllByTitleContainingIgnoreCase(anyString(), any(Pageable.class)))
                .thenReturn(filteredEntityPage);

        // Act
        GetAllGamesResponse response = getGamesUseCase.getGames(request);

        // Assert
        assertEquals(expectedFilteredGames, response.getGames());

        verify(gameRepository, times(1)).findAllByTitleContainingIgnoreCase(anyString(), any(Pageable.class));
        verify(gameRepository, never()).findAll(any(Pageable.class));
    }
}
