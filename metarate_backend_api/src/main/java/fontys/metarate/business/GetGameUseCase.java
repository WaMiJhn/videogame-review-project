package fontys.metarate.business;

import fontys.metarate.domain.game.Game;

import java.util.Optional;

public interface GetGameUseCase {
    Optional<Game> getGame(Long gameId);
}
