package study.wild.unittest.comment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.comment.controller.response.CommentResponse;
import study.wild.comment.domain.Comment;
import study.wild.comment.domain.CommentCreate;
import study.wild.comment.domain.CommentUpdate;
import study.wild.post.domain.Post;

import java.time.LocalDateTime;

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

    @Test
    @DisplayName("CommentUpdate로 Comment를 생성할 수 있다")
    void CommentUpdate로_Comment를_생성할_수_있다() {
        //given
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        Comment comment = Comment.builder()
                .post(post)
                .content("content")
                .build();
        CommentUpdate updateTest = CommentUpdate.builder()
                .content("updateTest")
                .build();
        //when
        Comment updatedComment = comment.update(post, updateTest);
        //then
        assertThat(updatedComment.getContent().content()).isEqualTo("updateTest");
    }

    @Test
    @DisplayName("Comment에 DeleteDate를 표시할 수 있다")
    void Comment에_DeleteDate를_표시할_수_있다() {
        //given
        LocalDateTime testDateTime = LocalDateTime.of(2023, 12, 31, 0, 0);
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        Comment comment = Comment.builder()
                .post(post)
                .content("commentContent")
                .build();
        //when
        Comment deleteCheckedComment = comment.delete(testDateTime);
        //then
        assertThat(deleteCheckedComment.getPost().getContent().content()).isEqualTo("content");
        assertThat(deleteCheckedComment.getPost().getTitle().title()).isEqualTo("title");
        assertThat(deleteCheckedComment.getDeletedDate()).isEqualTo(testDateTime);
        assertThat(deleteCheckedComment.getContent().content()).isEqualTo("commentContent");
    }
}
