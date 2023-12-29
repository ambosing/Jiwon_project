package study.wild.unittest.comment.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.wild.category.domain.Category;
import study.wild.comment.controller.response.CommentResponse;
import study.wild.comment.domain.CommentCreate;
import study.wild.post.domain.Post;
import study.wild.unittest.mock.comment.TestCommentContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentControllerTest {

    @Test
    @DisplayName("사용자는 댓글을 생성할 수 있다")
    void 사용자는_댓글을_생성할_수_있다() {
        //given
        TestCommentContainer container = TestCommentContainer.builder().build();
        Category category = Category.builder()
                .name("category")
                .build();
        Category savedCategory = container.categoryRepository.save(category);
        Post post = Post.builder()
                .title("title")
                .content("content")
                .category(savedCategory)
                .build();
        Post savedPost = container.postRepository.save(post);
        CommentCreate commentCreate = CommentCreate.builder()
                .content("comment")
                .postId(savedPost.getId())
                .build();
        //when
        ResponseEntity<CommentResponse> result = container.commentController.create(commentCreate);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1L);
        assertThat(result.getBody().getContent()).isEqualTo("comment");
    }

    @Test
    @DisplayName("사용자는 내용을 입력하지 않으면 댓글을 생성할 수 없다")
    void 사용자는_내용을_입력하지_않으면_댓글을_생성할_수_없다() {
        //given
        TestCommentContainer container = TestCommentContainer.builder().build();
        Category category = Category.builder()
                .name("category")
                .build();
        Category savedCategory = container.categoryRepository.save(category);
        Post post = Post.builder()
                .title("title")
                .content("content")
                .category(savedCategory)
                .build();
        Post savedPost = container.postRepository.save(post);
        CommentCreate commentCreate = CommentCreate.builder()
                .content("")
                .postId(savedPost.getId())
                .build();
        //when
        //then
        assertThatThrownBy(() -> container.commentController.create(commentCreate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용을 입력해주세요.");
    }
}
