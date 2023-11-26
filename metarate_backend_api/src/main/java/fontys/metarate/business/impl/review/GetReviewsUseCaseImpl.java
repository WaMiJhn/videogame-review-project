package fontys.metarate.business.impl.review;

import fontys.metarate.business.GetReviewsUseCase;
import fontys.metarate.domain.review.GetAllReviewsRequest;
import fontys.metarate.domain.review.GetAllReviewsResponse;
import fontys.metarate.domain.review.Review;
import fontys.metarate.persistence.ReviewRepository;
import fontys.metarate.persistence.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetReviewsUseCaseImpl implements GetReviewsUseCase {
    private final ReviewRepository reviewRepository;

    @Override
    public GetAllReviewsResponse getReviews(GetAllReviewsRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        Page<ReviewEntity> reviewPage;

        if (request.getUsername() != null && request.getGameId() != null) {
            reviewPage = reviewRepository.findAllByGameIdAndUserUsername(request.getGameId(), request.getUsername(), pageable);
        } else if (request.getUsername() != null) {
            reviewPage = reviewRepository.findAllByUserUsername(request.getUsername(), pageable);
        } else if (request.getGameId() != null) {
            reviewPage = reviewRepository.findAllByGameId(request.getGameId(), pageable);
        } else {
            reviewPage = reviewRepository.findAll(pageable);
        }

        List<Review> reviews = reviewPage
                .stream()
                .map(ReviewConverter::convert)
                .toList();

        final GetAllReviewsResponse response = new GetAllReviewsResponse();
        response.setReviews(reviews);
        return response;
    }
}




