package study.wild.comment.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long>, CommentQueryRepository {

    @Query("SELECT c " +
            "FROM CommentEntity c " +
            "JOIN FETCH c.user " +
            "JOIN FETCH c.post " +
            "WHERE c.id = :id")
    Optional<CommentEntity> findByIdAndDeletedDateIsNull(@Param("id") Long id);

}
