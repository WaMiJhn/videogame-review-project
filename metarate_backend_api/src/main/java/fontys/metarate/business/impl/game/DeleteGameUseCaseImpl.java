package fontys.metarate.business.impl.game;

import fontys.metarate.business.DeleteGameUseCase;
import fontys.metarate.business.exception.GameNotFoundException;
import fontys.metarate.persistence.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteGameUseCaseImpl implements DeleteGameUseCase {
    private final GameRepository gameRepository;

    @Override
    public void deleteGame(long gameId) {
        gameRepository.findById(gameId)
                .orElseThrow(GameNotFoundException::new);

        gameRepository.deleteById(gameId);
    }
}

