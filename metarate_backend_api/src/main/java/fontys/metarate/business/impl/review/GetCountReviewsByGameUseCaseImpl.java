package fontys.metarate.business.impl.review;

import fontys.metarate.business.GetCountReviewsByGameUseCase;
import fontys.metarate.persistence.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCountReviewsByGameUseCaseImpl implements GetCountReviewsByGameUseCase {
    private final ReviewRepository reviewRepository;
    @Override
    public Long getCountReviewsByGameId(Long gameId) {
        return reviewRepository.countByGameId(gameId);
    }
}
