package study.wild.post.infrastructure;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostQueryRepository {
    List<PostListQuery> findByCategoryId(Long categoryId, Pageable pageable);

    Optional<PostQuery> findWithCommentById(Long id);

    Long countByCategoryId(Long categoryId);
}
