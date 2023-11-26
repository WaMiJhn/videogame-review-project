package fontys.metarate.business;

import fontys.metarate.domain.game.GetAllGamesResponse;
import fontys.metarate.domain.game.GetGamesWithHighRatingRequest;

public interface GetGamesWithHighRatingUseCase {
    GetAllGamesResponse getGamesWithHighRating(GetGamesWithHighRatingRequest request);
}
