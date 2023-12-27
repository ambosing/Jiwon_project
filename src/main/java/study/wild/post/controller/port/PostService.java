package study.wild.post.controller.port;

import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;
import study.wild.post.domain.PostUpdate;

import java.util.List;

public interface PostService {
    // find는 Optional, get은 Null이면 에러
    Post getById(Long id);

    Post create(PostCreate postCreate);

    Post update(Long id, PostUpdate postUpdate);

    Long delete(Long id);

    List<Post> getByCategoryId(Long categoryId);
}
