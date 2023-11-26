package fontys.metarate.business.impl.game;

import fontys.metarate.business.GetGamesWithHighRatingUseCase;
import fontys.metarate.domain.game.Game;
import fontys.metarate.domain.game.GetAllGamesResponse;
import fontys.metarate.domain.game.GetGamesWithHighRatingRequest;
import fontys.metarate.persistence.GameRepository;
import fontys.metarate.persistence.entity.GameEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetGamesWithHighRatingUseCaseImpl implements GetGamesWithHighRatingUseCase {
    private final GameRepository gameRepository;
    @Override
    public GetAllGamesResponse getGamesWithHighRating(GetGamesWithHighRatingRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<GameEntity> gamePage = gameRepository.getGamesWithHighRating(pageable);
        List<Game> games = gamePage
                .stream()
                .map(GameConverter::convert)
                .toList();

        final GetAllGamesResponse response = new GetAllGamesResponse();
        response.setGames(games);
        return response;
    }
}
