package fontys.metarate.business.game;

import fontys.metarate.business.impl.game.GetCountGamesUseCaseImpl;
import fontys.metarate.domain.game.GetCountGamesRequest;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.GenreRepository;
import fontys.metarate.persistence.entity.GenreEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCountGamesUseCaseTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GetCountGamesUseCaseImpl getCountGamesUseCase;

    @Test
    void getCountGames_withGenreAndTitle_shouldReturnCount() {
        // Arrange
        String title = "Game";
        String genreName = "Action";
        Long expectedCount = 5L;

        GenreEntity genreEntity = GenreEntity.builder()
                .id(1L)
                .name(genreName)
                .build();

        when(genreRepository.findByName(genreName)).thenReturn(genreEntity);
        when(gameRepository.countByGenreAndTitleContainingIgnoreCase(genreEntity, title)).thenReturn(expectedCount);

        GetCountGamesRequest request = new GetCountGamesRequest();
        request.setTitle(title);
        request.setGenre(genreName);

        // Act
        Long count = getCountGamesUseCase.getCountGames(request);

        // Assert
        assertEquals(expectedCount, count);

        verify(genreRepository, times(1)).findByName(genreName);
        verify(gameRepository, times(1)).countByGenreAndTitleContainingIgnoreCase(genreEntity, title);
    }

    @Test
    void getCountGames_withGenre_shouldReturnCount() {
        // Arrange
        String genreName = "Action";
        Long expectedCount = 10L;

        GenreEntity genreEntity = GenreEntity.builder()
                .id(1L)
                .name(genreName)
                .build();

        when(genreRepository.findByName(genreName)).thenReturn(genreEntity);
        when(gameRepository.countByGenre(genreEntity)).thenReturn(expectedCount);

        GetCountGamesRequest request = new GetCountGamesRequest();
        request.setGenre(genreName);

        // Act
        Long count = getCountGamesUseCase.getCountGames(request);

        // Assert
        assertEquals(expectedCount, count);

        verify(genreRepository, times(1)).findByName(genreName);
        verify(gameRepository, times(1)).countByGenre(genreEntity);
    }

    @Test
    void getCountGames_withTitle_shouldReturnCount() {
        // Arrange
        String title = "Game";
        Long expectedCount = 15L;

        when(gameRepository.countByTitleContainingIgnoreCase(title)).thenReturn(expectedCount);

        GetCountGamesRequest request = new GetCountGamesRequest();
        request.setTitle(title);

        // Act
        Long count = getCountGamesUseCase.getCountGames(request);

        // Assert
        assertEquals(expectedCount, count);

        verify(gameRepository, times(1)).countByTitleContainingIgnoreCase(title);
    }

    @Test
    void getCountGames_withNoFilters_shouldReturnCount() {
        // Arrange
        Long expectedCount = 20L;

        when(gameRepository.count()).thenReturn(expectedCount);

        GetCountGamesRequest request = new GetCountGamesRequest();

        // Act
        Long count = getCountGamesUseCase.getCountGames(request);

        // Assert
        assertEquals(expectedCount, count);

        verify(gameRepository, times(1)).count();
    }
}

