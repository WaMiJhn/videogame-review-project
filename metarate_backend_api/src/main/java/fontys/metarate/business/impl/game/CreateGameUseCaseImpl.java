package fontys.metarate.business.impl.game;

import fontys.metarate.business.CreateGameUseCase;
import fontys.metarate.domain.game.CreateGameRequest;
import fontys.metarate.domain.game.CreateGameResponse;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateGameUseCaseImpl implements CreateGameUseCase {
    private final GameRepository gameRepository;

    @Override
    public CreateGameResponse createGame(CreateGameRequest request) {
        GameEntity savedGame = saveNewGame(request);

        return CreateGameResponse.builder()
                .id(savedGame.getId())
                .build();
    }
    private GameEntity saveNewGame(CreateGameRequest request) {
        GameEntity newGame = GameEntity.builder()
                .title(request.getTitle())
                .genre(GenreEntity.builder().id(request.getGenreId()).build())
                .description(request.getDescription())
                .trailerUrl(request.getTrailerUrl())
                .releaseYear(request.getReleaseYear())
                .developer(request.getDeveloper())
                .imageUrl(request.getImageUrl())
                .build();
        return gameRepository.save(newGame);
    }
}
