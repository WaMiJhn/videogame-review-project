package fontys.metarate.business.game;

import fontys.metarate.business.impl.genre.GenreConverter;
import fontys.metarate.business.impl.game.GameConverter;
import fontys.metarate.domain.game.Game;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GameConverterTest {
    @Test
    void testGameEntityConvert() {
        // Given
        GameEntity entity = new GameEntity();
        entity.setId(1L);
        entity.setTitle("Test Game");
        entity.setDeveloper("Test Developer");
        entity.setGenre(GenreEntity.builder().id(1L).name("Test Genre").build());
        entity.setReleaseYear(2022);
        entity.setDescription("Test description");
        entity.setImageUrl("https://test-url.com/image.jpg");
        entity.setTrailerUrl("https://test-url.com/trailer.mp4");

        // When
        Game game = GameConverter.convert(entity);

        // Then
        assertEquals(entity.getId(), game.getId());
        assertEquals(entity.getTitle(), game.getTitle());
        assertEquals(entity.getDeveloper(), game.getDeveloper());
        assertEquals(GenreConverter.convert(entity.getGenre()), game.getGenre());
        assertEquals(entity.getReleaseYear(), game.getReleaseYear());
        assertEquals(entity.getDescription(), game.getDescription());
        assertEquals(entity.getImageUrl(), game.getImageUrl());
        assertEquals(entity.getTrailerUrl(), game.getTrailerUrl());
    }
}
