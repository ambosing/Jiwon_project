package study.wild.post.service.port;

import study.wild.post.domain.Post;

public interface PostRepository {
    Post getById(Long id);

    Post save(Post post);

    Post update(Long id, Post post);

    Long delete(Long id);

}
