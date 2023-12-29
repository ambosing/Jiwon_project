package study.wild.comment.controller.port;

import study.wild.comment.domain.Comment;
import study.wild.comment.domain.CommentCreate;

public interface CommentService {
    Comment create(CommentCreate commentCreate);
}
