package study.wild.post.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.domain.Post;
import study.wild.post.service.port.PostRepository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;

    @Override
    public Post getById(Long id) {
        return postJpaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", id)).toDomain();
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post update(Long id, Post post) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }

}
