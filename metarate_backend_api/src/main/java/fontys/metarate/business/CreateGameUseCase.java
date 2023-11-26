package fontys.metarate.business;

import fontys.metarate.domain.game.CreateGameRequest;
import fontys.metarate.domain.game.CreateGameResponse;

public interface CreateGameUseCase {
    CreateGameResponse createGame(CreateGameRequest request);

}
