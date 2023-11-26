package fontys.metarate.business;

import fontys.metarate.domain.game.GetCountGamesRequest;

public interface GetCountGamesUseCase {
    public Long getCountGames(GetCountGamesRequest request);
}
