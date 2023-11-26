package fontys.metarate.business.impl.game;

import fontys.metarate.business.GetCountGamesUseCase;
import fontys.metarate.domain.game.GetCountGamesRequest;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.GenreRepository;
import fontys.metarate.persistence.entity.GenreEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCountGamesUseCaseImpl implements GetCountGamesUseCase {
    private GameRepository gameRepository;
    private GenreRepository genreRepository;

    @Override
    public Long getCountGames(GetCountGamesRequest request) {
        String title = request.getTitle();
        GenreEntity genre = genreRepository.findByName(request.getGenre());


        if (genre != null && title != null) {
            return gameRepository.countByGenreAndTitleContainingIgnoreCase(genre, title);
        } else if (genre != null) {
            return gameRepository.countByGenre(genre);
        } else if (title != null) {
            return gameRepository.countByTitleContainingIgnoreCase(title);
        } else {
            return gameRepository.count();
        }
    }
}

