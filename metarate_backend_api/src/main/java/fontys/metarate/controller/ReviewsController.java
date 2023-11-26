package fontys.metarate.controller;

import fontys.metarate.business.*;
import fontys.metarate.configuration.security.isauthenticated.IsAuthenticated;
import fontys.metarate.domain.review.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ReviewsController {
    private final CreateReviewUseCase createReviewUseCase;
    private final GetReviewsUseCase getReviewsUseCase;
    private final UpdateReviewUseCase updateReviewUseCase;
    private final DeleteReviewUseCase deleteReviewUseCase;
    private final GetCountReviewsByGameUseCase getCountReviewsByGameUseCase;
    private final GetCountReviewsByUserUseCase getCountReviewsByUserUseCase;

    @GetMapping
    public ResponseEntity<GetAllReviewsResponse> getAllReviews(@RequestParam(required = false) String username,
                                                               @RequestParam(required = false) Long gameId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        GetAllReviewsRequest request = GetAllReviewsRequest.builder()
                .username(username)
                .gameId(gameId)
                .page(page)
                .size(size)
                .build();
        GetAllReviewsResponse response = getReviewsUseCase.getReviews(request);
        return ResponseEntity.ok(response);
    }

    @IsAuthenticated
    @PostMapping()
    public ResponseEntity<CreateReviewResponse> createReview(@RequestBody @Valid CreateReviewRequest request) {
        CreateReviewResponse response = createReviewUseCase.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @IsAuthenticated
    @Transactional
    @PutMapping("{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable("reviewId") Long reviewId,
                                             @RequestBody @Valid UpdateReviewRequest request) {
        request.setId(reviewId);
        updateReviewUseCase.updateReview(request);
        return ResponseEntity.noContent().build();
    }
    @IsAuthenticated
    @DeleteMapping("{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        deleteReviewUseCase.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{gameId}/count-reviews-by-game")
    public Long getCountReviews(@PathVariable("gameId") Long gameId) {
        return getCountReviewsByGameUseCase.getCountReviewsByGameId(gameId);
    }
    @GetMapping("/{username}/count-reviews-by-user")
    public Long getCountReviewsByUser(@PathVariable("username") String username) {
        return getCountReviewsByUserUseCase.getCountReviewsByUser(username);
    }
}
