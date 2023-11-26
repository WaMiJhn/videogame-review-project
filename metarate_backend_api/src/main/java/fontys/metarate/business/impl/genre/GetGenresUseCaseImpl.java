package fontys.metarate.business.impl.genre;

import fontys.metarate.business.GetGenresUseCase;
import fontys.metarate.domain.genre.Genre;
import fontys.metarate.domain.genre.GetGenresResponse;
import fontys.metarate.persistence.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetGenresUseCaseImpl implements GetGenresUseCase {
    private final GenreRepository genreRepository;
    
    @Override
    public GetGenresResponse getGenres() {
        List<Genre> genres = genreRepository.findAll()
                .stream()
                .map(GenreConverter::convert)
                .toList();
        return  GetGenresResponse.builder()
                .genres(genres)
                .build();
    }
}
