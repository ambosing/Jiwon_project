package study.wild.comment.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.comment.controller.port.CommentService;
import study.wild.comment.domain.Comment;
import study.wild.comment.domain.CommentCreate;
import study.wild.comment.domain.CommentUpdate;
import study.wild.comment.service.port.CommentRepository;
import study.wild.common.service.DatetimeHolder;
import study.wild.post.domain.Post;
import study.wild.post.service.port.PostRepository;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final DatetimeHolder datetimeHolder;

    @Transactional
    public Comment create(CommentCreate commentCreate) {
        Post post = postRepository.getById(commentCreate.getPostId());
        Comment comment = Comment.fromCreate(post, commentCreate);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment update(Long id, CommentUpdate commentUpdate) {
        Post post = postRepository.getById(commentUpdate.postId());
        Comment comment = commentRepository.getById(id);
        Comment updatedComment = comment.update(post, commentUpdate);
        return commentRepository.save(updatedComment);
    }

    @Transactional
    public Long delete(Long id) {
        Comment comment = commentRepository.getById(id);
        Comment deletedComment = comment.delete(datetimeHolder.now());
        return commentRepository.save(deletedComment).getId();
    }
    
}
