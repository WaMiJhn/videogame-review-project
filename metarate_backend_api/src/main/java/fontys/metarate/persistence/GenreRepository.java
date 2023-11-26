package fontys.metarate.persistence;

import fontys.metarate.persistence.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    GenreEntity findByName(String name);
}
