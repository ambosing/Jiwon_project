package study.wild.post.service.port;

import study.wild.post.domain.Post;

import java.util.List;

public interface PostRepository {
    Post getById(Long id);

    Post save(Post post);

    Post update(Long id, Post post);

    List<Post> getByCategoryId(Long categoryId);
}
