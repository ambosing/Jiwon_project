package study.wild.comment.service.port;

import study.wild.comment.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Comment getById(Long id);

    Comment save(Comment comment);

    List<Comment> getByPostId(Long postId);
}
