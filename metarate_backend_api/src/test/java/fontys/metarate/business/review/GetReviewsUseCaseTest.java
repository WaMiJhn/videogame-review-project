package fontys.metarate.business.review;

import fontys.metarate.business.impl.review.GetReviewsUseCaseImpl;
import fontys.metarate.domain.game.Game;
import fontys.metarate.domain.genre.Genre;
import fontys.metarate.domain.review.GetAllReviewsRequest;
import fontys.metarate.domain.review.GetAllReviewsResponse;
import fontys.metarate.domain.review.Review;
import fontys.metarate.domain.user.User;
import fontys.metarate.persistence.ReviewRepository;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetReviewsUseCaseTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private GetReviewsUseCaseImpl getReviewsUseCase;

    @Test
    void getReviews_shouldReturnAllReviewsConverted() {
        // Arrange
        GetAllReviewsRequest request = new GetAllReviewsRequest();
        request.setPage(0);
        request.setSize(10);

        LocalDateTime now = LocalDateTime.now();

        GenreEntity genreEntity = GenreEntity.builder()
                .id(1L)
                .name("Genre 1")
                .build();

        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(ReviewEntity.builder()
                .id(1L)
                .rating(8.5)
                .date(now)
                .content("Review 1")
                .user(UserEntity.builder().username("User 1").build())
                .game(GameEntity.builder().genre(genreEntity).id(1L).build())
                .build());
        reviewEntities.add(ReviewEntity.builder()
                .id(2L)
                .rating(7.5)
                .date(now)
                .content("Review 2")
                .user(UserEntity.builder().username("User 2").build())
                .game(GameEntity.builder().genre(genreEntity).id(2L).build())
                .build());

        List<Review> expectedReviews = new ArrayList<>();
        Genre genre = Genre.builder().id(1L).name("Genre 1").build();
        expectedReviews.add(Review.builder()
                .id(1L)
                .rating(8.5)
                .date(now)
                .content("Review 1")
                .user(User.builder().username("User 1").build())
                .game(Game.builder().genre(genre).id(1L).build())
                .build());
        expectedReviews.add(Review.builder()
                .id(2L)
                .rating(7.5)
                .date(now)
                .content("Review 2")
                .user(User.builder().username("User 2").build())
                .game(Game.builder().genre(genre).id(2L).build())
                .build());

        Page<ReviewEntity> reviewEntityPage = new PageImpl<>(reviewEntities);

        when(reviewRepository.findAll(any(Pageable.class))).thenReturn(reviewEntityPage);

        // Act
        GetAllReviewsResponse response = getReviewsUseCase.getReviews(request);

        // Assert
        assertEquals(expectedReviews, response.getReviews());

        verify(reviewRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getReviews_shouldReturnFilteredByUser() {
        // Arrange
        GetAllReviewsRequest request = new GetAllReviewsRequest();
        request.setUsername("User 1");
        request.setPage(0);
        request.setSize(10);

        LocalDateTime now = LocalDateTime.now();
        GenreEntity genreEntity = GenreEntity.builder()
                .id(1L)
                .name("Genre 1")
                .build();
        Genre genre = Genre.builder().id(1L).name("Genre 1").build();

        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(ReviewEntity.builder()
                .id(1L)
                .rating(8.5)
                .date(now)
                .content("Review 1")
                .user(UserEntity.builder().username("User 1").build())
                .game(GameEntity.builder().genre(genreEntity).id(1L).build())
                .build());

        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(Review.builder()
                .id(1L)
                .rating(8.5)
                .date(now)
                .content("Review 1")
                .user(User.builder().username("User 1").build())
                .game(Game.builder().genre(genre).id(1L).build())
                .build());

        Page<ReviewEntity> reviewEntityPage = new PageImpl<>(reviewEntities);

        when(reviewRepository.findAllByUserUsername(eq("User 1"), any(Pageable.class))).thenReturn(reviewEntityPage);

        // Act
        GetAllReviewsResponse response = getReviewsUseCase.getReviews(request);

        // Assert
        assertEquals(expectedReviews, response.getReviews());

        verify(reviewRepository, times(1)).findAllByUserUsername(eq("User 1"), any(Pageable.class));
    }

    @Test
    void getReviews_shouldReturnFilteredByGame() {
        // Arrange
        GetAllReviewsRequest request = new GetAllReviewsRequest();
        request.setGameId(1L);
        request.setPage(0);
        request.setSize(10);

        LocalDateTime now = LocalDateTime.now();
        GenreEntity genreEntity = GenreEntity.builder()
                .id(1L)
                .name("Genre 1")
                .build();
        Genre genre = Genre.builder().id(1L).name("Genre 1").build();

        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(ReviewEntity.builder()
                .id(1L)
                .rating(8.5)
                .date(now)
                .content("Review 1")
                .user(UserEntity.builder().username("User 1").build())
                .game(GameEntity.builder().genre(genreEntity).id(1L).build())
                .build());

        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(Review.builder()
                .id(1L)
                .rating(8.5)
                .date(now)
                .content("Review 1")
                .user(User.builder().username("User 1").build())
                .game(Game.builder().genre(genre).id(1L).build())
                .build());

        Page<ReviewEntity> reviewEntityPage = new PageImpl<>(reviewEntities);

        when(reviewRepository.findAllByGameId(eq(1L), any(Pageable.class))).thenReturn(reviewEntityPage);

        // Act
        GetAllReviewsResponse response = getReviewsUseCase.getReviews(request);

        // Assert
        assertEquals(expectedReviews, response.getReviews());

        verify(reviewRepository, times(1)).findAllByGameId(eq(1L), any(Pageable.class));
    }

    @Test
    void getReviews_shouldReturnFilteredByUserAndGame() {
        // Arrange
        GetAllReviewsRequest request = new GetAllReviewsRequest();
        request.setUsername("User 1");
        request.setGameId(1L);
        request.setPage(0);
        request.setSize(10);

        LocalDateTime now = LocalDateTime.now();
        GenreEntity genreEntity = GenreEntity.builder()
                .id(1L)
                .name("Genre 1")
                .build();
        Genre genre = Genre.builder().id(1L).name("Genre 1").build();

        List<ReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(ReviewEntity.builder()
                .id(1L)
                .rating(8.5)
                .date(now)
                .content("Review 1")
                .user(UserEntity.builder().username("User 1").build())
                .game(GameEntity.builder().genre(genreEntity).id(1L).build())
                .build());

        List<Review> expectedReviews = new ArrayList<>();
        expectedReviews.add(Review.builder()
                .id(1L)
                .rating(8.5)
                .date(now)
                .content("Review 1")
                .user(User.builder().username("User 1").build())
                .game(Game.builder().genre(genre).id(1L).build())
                .build());

        Page<ReviewEntity> reviewEntityPage = new PageImpl<>(reviewEntities);

        when(reviewRepository.findAllByGameIdAndUserUsername(eq(1L), eq("User 1"), any(Pageable.class)))
                .thenReturn(reviewEntityPage);

        // Act
        GetAllReviewsResponse response = getReviewsUseCase.getReviews(request);

        // Assert
        assertEquals(expectedReviews, response.getReviews());

        verify(reviewRepository, times(1))
                .findAllByGameIdAndUserUsername(eq(1L), eq("User 1"), any(Pageable.class));
    }
}


