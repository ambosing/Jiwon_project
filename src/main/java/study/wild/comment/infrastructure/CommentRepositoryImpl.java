package study.wild.comment.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.wild.comment.domain.Comment;
import study.wild.comment.service.port.CommentRepository;
import study.wild.common.domain.ResourceNotFoundException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment getById(Long id) {
        return commentJpaRepository.findByIdAndDeletedDateIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id))
                .toDomain();
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).toDomain();
    }

    @Override
    public List<Comment> getByPostId(Long postId) {
        return commentJpaRepository.findByPostIdAndDeletedDateIsNull(postId)
                .stream()
                .map(CommentEntity::toDomain)
                .toList();
    }
}
