package fontys.metarate.business.review;

import fontys.metarate.business.impl.review.ReviewConverter;
import fontys.metarate.domain.review.Review;
import fontys.metarate.persistence.entity.GenreEntity;
import fontys.metarate.persistence.entity.ReviewEntity;
import fontys.metarate.persistence.entity.UserEntity;
import fontys.metarate.persistence.entity.GameEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewConverterTest {

    @Test
    void convert_shouldConvertReviewEntityToReview() {
        // Arrange
        long reviewId = 1L;
        int rating = 4;
        String content = "Great game!";
        LocalDateTime date = LocalDateTime.now();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("john_doe")
                .profilePic("https://example.com/profile_pic.jpg")
                .build();

        GameEntity gameEntity = GameEntity.builder()
                .id(1L)
                .title("Game 1")
                .developer("Developer 1")
                .releaseYear(2021)
                .description("Awesome game")
                .genre(GenreEntity.builder().id(1L).name("Genre 1").build())
                .build();

        ReviewEntity reviewEntity = ReviewEntity.builder()
                .id(reviewId)
                .rating(rating)
                .content(content)
                .date(date)
                .user(userEntity)
                .game(gameEntity)
                // Set other review properties if needed
                .build();

        // Act
        Review review = ReviewConverter.convert(reviewEntity);

        // Assert
        assertEquals(reviewId, review.getId());
        assertEquals(rating, review.getRating());
        assertEquals(content, review.getContent());
        assertEquals(date, review.getDate());
        // Assert user properties
        assertEquals(userEntity.getId(), review.getUser().getId());
        assertEquals(userEntity.getUsername(), review.getUser().getUsername());
        assertEquals(userEntity.getProfilePic(), review.getUser().getProfilePic());
        // Assert game properties
        assertEquals(gameEntity.getId(), review.getGame().getId());
        assertEquals(gameEntity.getTitle(), review.getGame().getTitle());
        assertEquals(gameEntity.getDeveloper(), review.getGame().getDeveloper());
        assertEquals(gameEntity.getReleaseYear(), review.getGame().getReleaseYear());
        assertEquals(gameEntity.getDescription(), review.getGame().getDescription());
        // Add assertions for other properties if needed
    }
}
