package fontys.metarate.business.impl.game;

import fontys.metarate.business.GetAverageGameRatingUseCase;
import fontys.metarate.persistence.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAverageGameRatingUseCaseImpl implements GetAverageGameRatingUseCase {
    private final GameRepository gameRepository;
    @Override
    public Double getAverageRatingByGameId(Long gameId) {
        return gameRepository.getAverageRatingByGameId(gameId);
    }
}
