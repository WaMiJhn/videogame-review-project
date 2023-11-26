package fontys.metarate.persistence;

import fontys.metarate.persistence.entity.GameEntity;
import fontys.metarate.persistence.entity.GenreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GameRepository extends JpaRepository<GameEntity, Long>{
    List<GameEntity> findByGenreName(String genreName);
    Page<GameEntity> findAllByGenreName(String genreName, Pageable pageable);
    Page<GameEntity> findAllByTitleContainingIgnoreCaseAndGenreName(String title, String genreName, Pageable pageable);
    Page<GameEntity> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    List<GameEntity> findByTitleContainingIgnoreCase(String title);
    @Query("SELECT AVG(r.rating) FROM ReviewEntity r JOIN r.game g WHERE g.id = :gameId")
    Double getAverageRatingByGameId(Long gameId);
    @Query("SELECT g FROM GameEntity g JOIN ReviewEntity r ON g.id = r.game.id GROUP BY g.id HAVING AVG(r.rating) >= 8.0 ORDER BY AVG(r.rating) DESC")
    Page<GameEntity> getGamesWithHighRating(Pageable pageable);

    Long countByGenreAndTitleContainingIgnoreCase(GenreEntity genre, String title);

    Long countByGenre(GenreEntity genre);

    Long countByTitleContainingIgnoreCase(String title);
}
