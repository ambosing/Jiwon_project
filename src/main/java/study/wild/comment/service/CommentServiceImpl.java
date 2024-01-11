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
import study.wild.common.service.port.DatetimeHolder;
import study.wild.post.domain.Post;
import study.wild.post.service.port.PostRepository;
import study.wild.user.domain.User;
import study.wild.user.service.port.UserRepository;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final DatetimeHolder datetimeHolder;
    private final UserRepository userRepository;


    @Transactional
    public Comment create(CommentCreate commentCreate) {
        Post post = postRepository.getById(commentCreate.getPostId());
        User user = userRepository.getByNo(commentCreate.getUserNo());
        Comment comment = Comment.fromCreate(post, user, commentCreate);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment update(Long id, CommentUpdate commentUpdate) {
        Comment comment = commentRepository.getById(id);
        Comment updatedComment = comment.update(commentUpdate);
        return commentRepository.save(updatedComment);
    }

    @Transactional
    public Long delete(Long id) {
        Comment comment = commentRepository.getById(id);
        System.out.println(comment.getPost());
        System.out.println(comment.getUser());
        Comment deletedComment = comment.delete(datetimeHolder.now());
        return commentRepository.save(deletedComment).getId();
    }

}
