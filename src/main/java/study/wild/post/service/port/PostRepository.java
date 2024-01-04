package study.wild.post.service.port;

import org.springframework.data.domain.Pageable;
import study.wild.post.domain.Post;
import study.wild.post.infrastructure.PostListQuery;
import study.wild.post.infrastructure.PostQuery;

import java.util.List;

public interface PostRepository {
    Post getById(Long id);

    List<Post> getByIdIn(List<Long> ids);

    Post save(Post post);

    Long countByCategoryId(Long categoryId);

    List<PostListQuery> getByCategoryId(Long categoryId, Pageable pageable);

    PostQuery getWithCommentById(Long id);

    Integer saveAll(List<Post> updatedPosts);
}
