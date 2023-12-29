package study.wild.comment.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.wild.comment.domain.Comment;
import study.wild.comment.service.port.CommentRepository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).toDomain();
    }
}
