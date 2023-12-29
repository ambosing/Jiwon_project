package study.wild.comment.service.port;

import study.wild.comment.domain.Comment;

public interface CommentRepository {
    Comment save(Comment comment);
}
