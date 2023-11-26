package fontys.metarate.business.genre;

import fontys.metarate.business.impl.genre.GenreConverter;
import fontys.metarate.business.impl.genre.GetGenresUseCaseImpl;
import fontys.metarate.domain.genre.Genre;
import fontys.metarate.domain.genre.GetGenresResponse;
import fontys.metarate.persistence.GenreRepository;
import fontys.metarate.persistence.entity.GenreEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGenresUseCaseTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GetGenresUseCaseImpl getGenresUseCase;

    @Test
    void testGetGenres() {
        // Prepare
        List<GenreEntity> genreEntities = new ArrayList<>();
        genreEntities.add(new GenreEntity(1L, "Action"));
        genreEntities.add(new GenreEntity(2L, "Comedy"));

        when(genreRepository.findAll()).thenReturn(genreEntities);

        // Act
        GetGenresResponse response = getGenresUseCase.getGenres();

        // Assert
        List<Genre> expectedGenres = genreEntities.stream()
                .map(GenreConverter::convert)
                .collect(Collectors.toList());
        assertEquals(expectedGenres, response.getGenres());
    }
}