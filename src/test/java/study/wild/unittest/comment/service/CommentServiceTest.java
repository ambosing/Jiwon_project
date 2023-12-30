package study.wild.unittest.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.comment.controller.port.CommentService;
import study.wild.comment.domain.Comment;
import study.wild.comment.domain.CommentCreate;
import study.wild.comment.domain.CommentUpdate;
import study.wild.comment.service.CommentServiceImpl;
import study.wild.comment.service.port.CommentRepository;
import study.wild.post.domain.Post;
import study.wild.post.service.port.PostRepository;
import study.wild.unittest.mock.comment.FakeCommentRepository;
import study.wild.unittest.mock.post.FakePostRepository;

import static org.assertj.core.api.Assertions.assertThat;

class CommentServiceTest {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        postRepository = new FakePostRepository();
        commentRepository = new FakeCommentRepository();
        commentService = CommentServiceImpl.builder()
                .commentRepository(commentRepository)
                .postRepository(postRepository)
                .build();
    }

    @Test
    @DisplayName("새로운 Comment를 생성할 수 있다")
    void 새로운_Comment를_생성할_수_있다() {
        //given
        Post post = postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .build()
        );

        CommentCreate commentCreate = CommentCreate.builder()
                .content("content")
                .postId(post.getId())
                .build();
        //when
        Comment comment = commentService.create(commentCreate);
        //then
        assertThat(comment.getId()).isNotNull();
        assertThat(comment.getPost()).isEqualTo(post);
        assertThat(comment.getContent().content()).isEqualTo("content");
    }

    @Test
    @DisplayName("Comment를 수정할 수 있다")
    void Comment를_수정할_수_있다() {
        //given
        Post post = postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .build()
        );
        Comment comment = Comment.builder()
                .content("content")
                .post(post)
                .build();
        Comment savedComment = commentRepository.save(comment);
        CommentUpdate commentUpdate = CommentUpdate.builder()
                .content("updateTest")
                .postId(post.getId())
                .build();
        //when
        Comment updatedComment = commentService.update(savedComment.getId(), commentUpdate);
        //then
        assertThat(updatedComment.getContent().content()).isEqualTo("updateTest");
        assertThat(updatedComment.getPost().getContent().content()).isEqualTo("content");
        assertThat(updatedComment.getPost().getTitle().title()).isEqualTo("title");
    }
}
