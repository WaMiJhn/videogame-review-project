package fontys.metarate.persistence;

import fontys.metarate.persistence.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Page<ReviewEntity> findAllByUserUsername(String username, Pageable pageable);
    Page<ReviewEntity> findAllByGameId(Long gameId, Pageable pageable);
    Page<ReviewEntity> findAllByGameIdAndUserUsername(Long gameId, String username, Pageable pageable);

    Long countByGameId(Long gameId);
    Long countByUserUsername(String username);

}
