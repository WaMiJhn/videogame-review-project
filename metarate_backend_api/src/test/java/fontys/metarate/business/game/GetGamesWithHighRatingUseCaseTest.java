package fontys.metarate.business.game;

import fontys.metarate.business.impl.game.GetGamesWithHighRatingUseCaseImpl;
import fontys.metarate.domain.game.Game;
import fontys.metarate.domain.game.GetAllGamesResponse;
import fontys.metarate.domain.game.GetGamesWithHighRatingRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetGamesWithHighRatingUseCaseTest {
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GetGamesWithHighRatingUseCaseImpl getGamesWithHighRatingUseCase;

    @Test
    void getGamesWithHighRating_shouldReturnGamesWithHighRating() {
        // Arrange
        int page = 0;
        int size = 10;

        //Create user
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("User 1")
                .password("Password 1")
                .build();

        // Create genre
        GenreEntity genreEntity = GenreEntity.builder()
                .id(1L)
                .name("Action")
                .build();

        // Create games
        GameEntity game1 = GameEntity.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .genre(genreEntity)
                .releaseYear(2020)
                .description("Description 1")
                .imageUrl("https://example.com/image1.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build();

        GameEntity game2 = GameEntity.builder()
                .id(2L)
                .title("Game 2")
                .developer("Developer 2")
                .genre(genreEntity)
                .releaseYear(2021)
                .description("Description 2")
                .imageUrl("https://example.com/image2.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build();

        GameEntity game3 = GameEntity.builder()
                .id(3L)
                .title("Game 3")
                .developer("Developer 3")
                .genre(genreEntity)
                .releaseYear(2022)
                .description("Description 3")
                .imageUrl("https://example.com/image3.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build();

        GameEntity game4 = GameEntity.builder()
                .id(4L)
                .title("Game 4")
                .developer("Developer 4")
                .genre(genreEntity)
                .releaseYear(2023)
                .description("Description 4")
                .imageUrl("https://example.com/image4.jpg")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build();

        // Create reviews
        ReviewEntity review1 = ReviewEntity.builder()
                .id(1L)
                .rating(9.5)
                .date(LocalDateTime.now())
                .content("Great game!")
                .user(userEntity)
                .game(game1)
                .build();

        ReviewEntity review2 = ReviewEntity.builder()
                .id(2L)
                .rating(8.8)
                .date(LocalDateTime.now())
                .content("Fun game!")
                .user(userEntity)
                .game(game2)
                .build();

        ReviewEntity review3 = ReviewEntity.builder()
                .id(3L)
                .rating(7.2)
                .date(LocalDateTime.now())
                .content("Decent game.")
                .user(userEntity)
                .game(game3)
                .build();

        ReviewEntity review4 = ReviewEntity.builder()
                .id(4L)
                .rating(8.2)
                .date(LocalDateTime.now())
                .content("Enjoyable game.")
                .user(userEntity)
                .game(game4)
                .build();

        // Create page with games
        List<GameEntity> gamesWithHighRating = List.of(game1, game2);
        Page<GameEntity> gamePage = new PageImpl<>(gamesWithHighRating);

        when(gameRepository.getGamesWithHighRating(any(Pageable.class))).thenReturn(gamePage);

        GetGamesWithHighRatingRequest request = new GetGamesWithHighRatingRequest();
        request.setPage(page);
        request.setSize(size);

        // Act
        GetAllGamesResponse response = getGamesWithHighRatingUseCase.getGamesWithHighRating(request);

        // Assert
        assertEquals(gamesWithHighRating.size(), response.getGames().size());
        for (int i = 0; i < gamesWithHighRating.size(); i++) {
            GameEntity gameEntity = gamesWithHighRating.get(i);
            Game game = response.getGames().get(i);

            assertEquals(gameEntity.getId(), game.getId());
            assertEquals(gameEntity.getTitle(), game.getTitle());
        }

        verify(gameRepository, times(1)).getGamesWithHighRating(any(Pageable.class));
    }
}

