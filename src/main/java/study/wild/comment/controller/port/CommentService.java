package study.wild.comment.controller.port;

import study.wild.comment.domain.Comment;
import study.wild.comment.domain.CommentCreate;
import study.wild.comment.domain.CommentUpdate;

public interface CommentService {
    Comment create(CommentCreate commentCreate);

    Comment update(Long id, CommentUpdate commentUpdate);

    Long delete(Long id);
}
