package fontys.metarate.business.impl.game;

import fontys.metarate.business.UpdateGameUseCase;
import fontys.metarate.business.exception.GameNotFoundException;
import fontys.metarate.domain.game.UpdateGameRequest;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateGameUseCaseImpl implements UpdateGameUseCase {
    private final GameRepository gameRepository;

    @Override
    public void updateGame(UpdateGameRequest request) {
        Optional<GameEntity> gameOptional = gameRepository.findById(request.getId());
        if (gameOptional.isEmpty()) {
            throw new GameNotFoundException();
        }
        GameEntity game = gameOptional.get();
        updateFields(request, game);
    }

    private void updateFields(UpdateGameRequest request, GameEntity game) {
        game.setTitle(request.getTitle());
        game.setDeveloper(request.getDeveloper());
        game.setGenre(GenreEntity.builder().id(request.getGenreId()).build());
        game.setReleaseYear(request.getReleaseYear());
        game.setDescription(request.getDescription());
        game.setImageUrl(request.getImageUrl());
        game.setTrailerUrl(request.getTrailerUrl());

        gameRepository.save(game);
    }
}

