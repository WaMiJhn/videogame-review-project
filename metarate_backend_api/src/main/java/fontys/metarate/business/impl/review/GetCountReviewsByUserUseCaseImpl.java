package fontys.metarate.business.impl.review;

import fontys.metarate.business.GetCountReviewsByUserUseCase;
import fontys.metarate.persistence.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCountReviewsByUserUseCaseImpl implements GetCountReviewsByUserUseCase {
    private final ReviewRepository reviewRepository;
    @Override
    public Long getCountReviewsByUser(String username) {
        return reviewRepository.countByUserUsername(username);
    }
}
