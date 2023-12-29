package study.wild.unittest.comment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.comment.controller.response.CommentResponse;
import study.wild.comment.domain.Comment;
import study.wild.comment.domain.CommentCreate;
import study.wild.post.domain.Post;

import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {
    @Test
    @DisplayName("CommentCreate로 Comment를 만들 수 있다")
    void CommentCreate로_Comment를_만들_수_있다() {
        //given
        CommentCreate commentCreate = CommentCreate.builder()
                .postId(1L)
                .content("content")
                .build();
        Post post = Post.builder()
                .id(1L)
                .title("title")
                .content("content")
                .build();
        //when
        Comment comment = Comment.fromCreate(post, commentCreate);
        //then
        assertThat(comment.getContent().content()).isEqualTo("content");
        assertThat(comment.getPost().getId()).isEqualTo(1L);
        assertThat(comment.getPost().getTitle().title()).isEqualTo("title");
        assertThat(comment.getPost().getContent().content()).isEqualTo("content");
    }

    @Test
    @DisplayName("Comment로 CommentResponse를 만들 수 있다")
    void Comment로_CommentResponse를_만들_수_있다() {
        //given
        Comment comment = Comment.builder()
                .id(1L)
                .content("content")
                .build();
        //when
        CommentResponse commentResponse = CommentResponse.from(comment);
        //then
        assertThat(commentResponse.getId()).isEqualTo(1L);
        assertThat(commentResponse.getContent()).isEqualTo("content");
    }
}
