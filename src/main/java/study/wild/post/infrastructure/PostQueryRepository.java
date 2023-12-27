package study.wild.post.infrastructure;

import org.springframework.data.domain.Pageable;
import study.wild.post.controller.response.PostListResponse;

import java.util.List;

public interface PostQueryRepository {
    List<PostListResponse> findByCategoryId(Long categoryId, Pageable pageable);

    Long countByCategoryId(Long categoryId);
}
