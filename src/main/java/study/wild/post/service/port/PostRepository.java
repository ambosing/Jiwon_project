package study.wild.post.service.port;

import org.springframework.data.domain.Pageable;
import study.wild.post.controller.response.PostListResponse;
import study.wild.post.domain.Post;

import java.util.List;

public interface PostRepository {
    Post getById(Long id);

    Post save(Post post);

    Long countByCategoryId(Long categoryId);

    List<PostListResponse> getByCategoryId(Long categoryId, Pageable pageable);
}
