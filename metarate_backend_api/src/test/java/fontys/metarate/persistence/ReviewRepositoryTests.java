package fontys.metarate.persistence;

import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import fontys.metarate.persistence.entity.ReviewEntity;
import fontys.metarate.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindAllByUserUsername() {
        GameEntity game1 = createGame(1L, "Sample Game");
        GameEntity game2 = createGame(100L, "Sample Game");
        UserEntity user = createUser(2L, "user1");
        createTestReview(1L, 8.0, LocalDateTime.now(), "Great game!", game1, user);
        createTestReview(2L, 7.5, LocalDateTime.now(), "Good game!", game2, user);

        Page<ReviewEntity> reviews = reviewRepository.findAllByUserUsername("user1", Pageable.unpaged());
        assertEquals(2, reviews.getTotalElements());
        assertTrue(reviews.stream().allMatch(review -> review.getUser().getUsername().equals("user1")));
    }

    @Test
    void testFindAllByGameId() {
        GameEntity game1 = createGame(2L, "Sample Game");
        UserEntity user = createUser(1L, "user2");
        UserEntity user2 = createUser(69L, "user3");

        createTestReview(11L, 8.0, LocalDateTime.now(), "Great game!", game1, user);
        createTestReview(12L, 7.5, LocalDateTime.now(), "Good game!", game1, user2);

        Page<ReviewEntity> reviews = reviewRepository.findAllByGameId(game1.getId(), Pageable.unpaged());
        assertEquals(2, reviews.getTotalElements());
        assertTrue(reviews.stream().allMatch(review -> review.getGame().getId().equals(game1.getId())));
    }

    @Test
    void testFindAllByGameIdAndUserUsername() {
        GameEntity game = createGame(3L, "Sample Game");
        UserEntity user = createUser(10L, "john");

        createTestReview(21L, 8.0, LocalDateTime.now(), "Great game!", game, user);

        Page<ReviewEntity> reviews = reviewRepository.findAllByGameIdAndUserUsername(game.getId(), "john", Pageable.unpaged());
        assertEquals(1, reviews.getTotalElements());
        assertTrue(reviews.stream().allMatch(review ->
                review.getGame().getId().equals(game.getId()) && review.getUser().getUsername().equals("john")));
    }

    private ReviewEntity createTestReview(Long id, double rating, LocalDateTime date, String content, GameEntity game, UserEntity user) {

        ReviewEntity review = ReviewEntity.builder()
                .id(id)
                .rating(rating)
                .date(date)
                .content(content)
                .game(game)
                .user(user)
                .build();

        return entityManager.merge(review);
    }

    private GameEntity createGame(Long id, String title) {
        GenreEntity genre = GenreEntity.builder()
                .name("Adventure")
                .build();
        genre = entityManager.merge(genre);

        GameEntity game = GameEntity.builder()
                .id(id)
                .title(title)
                .developer("Sample Developer")
                .genre(genre)
                .releaseYear(2022)
                .description("Sample description")
                .imageUrl("sample_image_url")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build();
        return  entityManager.merge(game);
    }

    private UserEntity createUser(Long id, String username) {
        UserEntity user = UserEntity.builder()
                .id(id)
                .username(username)
                .password("password")
                .build();

        return entityManager.merge(user);
    }



}
