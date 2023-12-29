package study.wild.unittest.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.comment.controller.port.CommentService;
import study.wild.comment.domain.Comment;
import study.wild.comment.domain.CommentCreate;
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
    @Disabled
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
}
