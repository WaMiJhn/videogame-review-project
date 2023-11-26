package fontys.metarate.business.impl.genre;

import fontys.metarate.domain.genre.Genre;
import fontys.metarate.persistence.entity.GenreEntity;

public class GenreConverter {
    private GenreConverter() {

    }
    public static Genre convert(GenreEntity genre) {
        return Genre.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
