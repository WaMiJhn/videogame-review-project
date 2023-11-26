package fontys.metarate.business.impl.game;

import fontys.metarate.business.GetGamesUseCase;
import fontys.metarate.domain.game.Game;
import fontys.metarate.domain.game.GetAllGamesRequest;
import fontys.metarate.domain.game.GetAllGamesResponse;
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
public class GetGamesUseCaseImpl implements GetGamesUseCase {
    private final GameRepository gameRepository;

    @Override
    public GetAllGamesResponse getGames(final GetAllGamesRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<GameEntity> gamePage;

        if (request.getGenreName() != null && request.getTitle() != null) {
            gamePage = gameRepository.findAllByTitleContainingIgnoreCaseAndGenreName(request.getTitle(), request.getGenreName(), pageable);
        } else if (request.getGenreName() != null) {
            gamePage = gameRepository.findAllByGenreName(request.getGenreName(), pageable);
        } else if (request.getTitle() != null) {
            gamePage = gameRepository.findAllByTitleContainingIgnoreCase(request.getTitle(), pageable);
        } else {
            gamePage = gameRepository.findAll(pageable);
        }

        List<Game> games = gamePage
                .stream()
                .map(GameConverter::convert)
                .toList();

        final GetAllGamesResponse response = new GetAllGamesResponse();
        response.setGames(games);
        return response;
    }
}

