package fontys.metarate.business.impl.game;

import fontys.metarate.business.impl.genre.GenreConverter;
import fontys.metarate.domain.game.Game;
import fontys.metarate.persistence.entity.GameEntity;

public final class GameConverter {
    private GameConverter() {
    }
    public static Game convert(GameEntity game) {
        return  Game.builder()
                .id(game.getId())
                .description(game.getDescription())
                .genre(GenreConverter.convert(game.getGenre()))
                .title(game.getTitle())
                .developer(game.getDeveloper())
                .releaseYear(game.getReleaseYear())
                .imageUrl(game.getImageUrl())
                .trailerUrl(game.getTrailerUrl())
                .build();
    }
}
