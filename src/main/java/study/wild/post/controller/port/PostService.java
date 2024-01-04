package study.wild.post.controller.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;
import study.wild.post.domain.PostUpdate;
import study.wild.post.infrastructure.PostListQuery;
import study.wild.post.infrastructure.PostQuery;

public interface PostService {
    // find는 Optional, get은 Null이면 에러
    Post getById(Long id);

    PostQuery getByIdWithComment(Long id);

    Post create(PostCreate postCreate);

    Post update(Long id, PostUpdate postUpdate);

    Long delete(Long id);

    Page<PostListQuery> getByCategoryId(Long categoryId, Long totalCount, Pageable pageable);
}
