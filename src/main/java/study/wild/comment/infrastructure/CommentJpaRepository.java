package study.wild.comment.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findByIdAndDeletedDateIsNull(Long id);

    List<CommentEntity> findByPostIdAndDeletedDateIsNull(Long postId);
}
