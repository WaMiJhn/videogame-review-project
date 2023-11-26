package fontys.metarate.business.impl.review;

import fontys.metarate.business.impl.game.GameConverter;
import fontys.metarate.business.impl.user.UserConverter;
import fontys.metarate.domain.review.Review;
import fontys.metarate.persistence.entity.ReviewEntity;

public final class ReviewConverter {
    private ReviewConverter() {
    }

    public static Review convert(ReviewEntity review) {
        return Review.builder()
                .id(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .date(review.getDate())
                .user(UserConverter.convert(review.getUser()))
                .game(GameConverter.convert(review.getGame()))
                .build();
    }
}

