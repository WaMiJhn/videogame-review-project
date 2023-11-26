package fontys.metarate.domain.review;

import fontys.metarate.domain.game.Game;
import fontys.metarate.domain.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Review {
    private Long id;
    private double rating;
    private LocalDateTime date;
    private String content;
    private User user;
    private Game game;
}
