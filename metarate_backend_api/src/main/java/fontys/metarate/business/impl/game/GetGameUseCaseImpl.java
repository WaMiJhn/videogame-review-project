package fontys.metarate.business.impl.game;

import fontys.metarate.business.GetGameUseCase;
import fontys.metarate.business.exception.GameNotFoundException;
import fontys.metarate.domain.game.Game;
import fontys.metarate.persistence.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetGameUseCaseImpl implements GetGameUseCase {
    private final GameRepository gameRepository;

    @Override
    public Optional<Game> getGame(Long gameId) {
        return Optional.of(gameRepository.findById(gameId)
                .map(GameConverter::convert)
                .orElseThrow(GameNotFoundException::new));
    }
}
