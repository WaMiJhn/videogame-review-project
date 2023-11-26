package fontys.metarate.business;

import fontys.metarate.domain.game.GetAllGamesRequest;
import fontys.metarate.domain.game.GetAllGamesResponse;

public interface GetGamesUseCase {
    GetAllGamesResponse getGames(GetAllGamesRequest request);
}
