package study.wild.comment.service.port;

import study.wild.comment.domain.Comment;
import study.wild.comment.infrastructure.CommentQuery;

import java.util.List;

public interface CommentRepository {

    Comment getById(Long id);

    Comment save(Comment comment);

    List<CommentQuery> getByPostId(Long postIds);
}
