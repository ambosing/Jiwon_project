package study.wild.post.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.domain.Post;
import study.wild.post.service.port.PostRepository;

import java.util.List;

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
    public List<Post> getByIdIn(List<Long> ids) {
        return postJpaRepository.findByIdIn(ids)
                .stream()
                .map(PostEntity::toDomain)
                .toList();
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository
                .save(PostEntity.from(post))
                .toDomain();
    }

    @Override
    public Long countByCategoryId(Long categoryId) {
        return postJpaRepository.countByCategoryId(categoryId);
    }

    @Override
    public List<PostListQuery> getByCategoryId(Long categoryId, Pageable pageable) {
        return postJpaRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public PostQuery getWithCommentById(Long id) {
        return postJpaRepository
                .findWithCommentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
    }

    @Override
    public Integer saveAll(List<Post> updatedPosts) {
        List<PostEntity> postEntities = updatedPosts.stream()
                .map(PostEntity::from)
                .toList();
        return postJpaRepository.saveAll(postEntities).size();
    }

}
