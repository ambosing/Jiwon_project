package study.wild.post.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.domain.Post;
import study.wild.post.service.port.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final PostJpaRepository postJpaRepository;

    @Override
    public Post getById(Long id) {
        return postJpaRepository
                .findByIdAndDeletedDateIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id))
                .toDomain();
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository
                .save(PostEntity.from(post))
                .toDomain();
    }

    @Override
    public Post update(Long id, Post post) {
        return null;
    }

    @Override
    public List<Post> getByCategoryId(Long categoryId) {
        return postJpaRepository.findByCategoryId(categoryId)
                .stream()
                .map(PostEntity::toDomain)
                .collect(Collectors.toList());
    }

}
