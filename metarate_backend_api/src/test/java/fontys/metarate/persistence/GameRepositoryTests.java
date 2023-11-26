package fontys.metarate.persistence;

import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameRepositoryTests {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindByGenreName() {
        GenreEntity genre = GenreEntity.builder()
                .id(1L)
                .name("Adventure")
                .build();
        genre = entityManager.merge(genre);

        List<GameEntity> expected = List.of(
                createTestGame(1L, "Sample Game", genre),
                createTestGame(3L, "Puzzle Game", genre));
        List<GameEntity> actual = gameRepository.findByGenreName("Adventure");
        assertEquals(expected, actual);
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
        GenreEntity genre = GenreEntity.builder()
                .id(1L)
                .name("Adventure")
                .build();
        genre = entityManager.merge(genre);

        List<GameEntity> expected = List.of(
                createTestGame(1L, "Sample Game", genre),
                createTestGame(4L, "Strategy Game", genre)
        );
        List<GameEntity> actual = gameRepository.findByTitleContainingIgnoreCase("s");
        assertEquals(expected, actual);
    }
    @Test
    void testCountByGenreAndTitleContainingIgnoreCase() {
        GenreEntity genre = GenreEntity.builder()
                .id(1L)
                .name("Adventure")
                .build();
        genre = entityManager.merge(genre);

        createTestGame(31L, "Sample Game", genre);
        createTestGame(32L, "Action Game", genre);
        createTestGame(33L, "Puzzle Game", genre);
        createTestGame(34L, "Strategy Game", genre);

        String title = "game";
        Long expectedCount = 4L;
        Long actualCount = gameRepository.countByGenreAndTitleContainingIgnoreCase(genre, title);
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testCountByGenre() {
        GenreEntity genre = GenreEntity.builder()
                .id(1L)
                .name("Adventure")
                .build();
        genre = entityManager.merge(genre);

        createTestGame(21L, "Sample Game", genre);
        createTestGame(22L, "Action Game", genre);
        createTestGame(23L, "Puzzle Game", genre);
        createTestGame(24L, "Strategy Game", genre);

        Long expectedCount = 4L;
        Long actualCount = gameRepository.countByGenre(genre);
        assertEquals(expectedCount, actualCount);
    }

    @Test
    void testCountByTitleContainingIgnoreCase() {
        GenreEntity genre = GenreEntity.builder()
                .id(1L)
                .name("Adventure")
                .build();
        genre = entityManager.merge(genre);

        createTestGame(11L, "Sample Game", genre);
        createTestGame(12L, "Action Game", genre);
        createTestGame(13L, "Puzzle Game", genre);
        createTestGame(14L, "Strategy Game", genre);

        String title = "game";
        Long expectedCount = 4L;
        Long actualCount = gameRepository.countByTitleContainingIgnoreCase(title);
        assertEquals(expectedCount, actualCount);
    }


    private GameEntity createTestGame(Long id, String title, GenreEntity genre) {
        GameEntity gameEntity = GameEntity.builder()
                .id(id)
                .title(title)
                .developer("Sample Developer")
                .genre(genre)
                .releaseYear(2022)
                .description("Sample description")
                .imageUrl("sample_image_url")
                .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                .build();

        System.out.println("Creating test game: " + gameEntity);

        return entityManager.merge(gameEntity);
    }


}





