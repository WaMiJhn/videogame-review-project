package fontys.metarate.business.genre;

import fontys.metarate.business.impl.genre.GenreConverter;
import fontys.metarate.domain.genre.Genre;
import fontys.metarate.persistence.entity.GenreEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GenreConverterTest {
    @Test
    void testConvert() {
        // Prepare
        GenreEntity genreEntity = new GenreEntity(1L, "Action");

        // Act
        Genre genre = GenreConverter.convert(genreEntity);

        // Assert
        assertEquals(genreEntity.getId(), genre.getId());
        assertEquals(genreEntity.getName(), genre.getName());
    }
}
