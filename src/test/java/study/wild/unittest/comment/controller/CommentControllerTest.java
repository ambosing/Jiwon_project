package study.wild.unittest.comment.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.wild.category.domain.Category;
import study.wild.comment.controller.response.CommentResponse;
import study.wild.comment.domain.Comment;
import study.wild.comment.domain.CommentCreate;
import study.wild.comment.domain.CommentUpdate;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.domain.Post;
import study.wild.unittest.mock.comment.TestCommentContainer;
import study.wild.user.domain.User;

import java.time.LocalDateTime;

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
        User user = User.builder()
                .no(1L)
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        User savedUser = container.userRepository.save(user);
        CommentCreate commentCreate = CommentCreate.builder()
                .content("comment")
                .postId(savedPost.getId())
                .userNo(savedUser.getNo())
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
        User user = User.builder()
                .no(1L)
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        User savedUser = container.userRepository.save(user);
        CommentCreate commentCreate = CommentCreate.builder()
                .content("")
                .postId(savedPost.getId())
                .userNo(savedUser.getNo())
                .build();
        //when
        //then
        assertThatThrownBy(() -> container.commentController.create(commentCreate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용을 입력해주세요.");
    }

    @Test
    @DisplayName("사용자는 댓글의 내용을 수정할 수 있다")
    void 사용자는_댓글의_내용을_수정할_수_있다() {
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
        User user = User.builder()
                .no(1L)
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        User savedUser = container.userRepository.save(user);
        Comment comment = Comment.builder()
                .content("create")
                .post(savedPost)
                .user(savedUser)
                .build();
        Comment savedComment = container.commentRepository.save(comment);
        CommentUpdate updatedComment = CommentUpdate.builder()
                .postId(savedPost.getId())
                .userNo(savedUser.getNo())
                .content("update")
                .build();
        //when
        ResponseEntity<CommentResponse> result = container.commentController.update(savedComment.getId(), updatedComment);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("update");
    }

    @Test
    @DisplayName("사용자는 내용이 없이 댓글을 수정할 수 없다")
    void 사용자는_내용이_없이_댓글을_수정할_수_없다() {
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
        User user = User.builder()
                .no(1L)
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        User savedUser = container.userRepository.save(user);
        Comment comment = Comment.builder()
                .content("create")
                .post(savedPost)
                .user(savedUser)
                .build();
        Comment savedComment = container.commentRepository.save(comment);
        CommentUpdate commentUpdate = CommentUpdate.builder()
                .postId(savedPost.getId())
                .userNo(savedUser.getNo())
                .content("")
                .build();
        //when
        //then
        assertThatThrownBy(() -> container.commentController.update(savedComment.getId(), commentUpdate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용을 입력해주세요.");
    }

    @Test
    @DisplayName("사용자는 댓글을 삭제할 수 있다")
    void 사용자는_댓글을_삭제할_수_있다() {
        //given
        TestCommentContainer container = TestCommentContainer.builder()
                .datetimeHolder(() -> LocalDateTime.of(2023, 12, 31, 0, 0))
                .build();
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
        Comment comment = Comment.builder()
                .content("create")
                .post(savedPost)
                .build();
        Comment savedComment = container.commentRepository.save(comment);
        //when
        ResponseEntity<Long> result = container.commentController.delete(savedComment.getId());
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(savedComment.getId());
    }

    @Test
    @DisplayName("사용자는 없는 댓글을 삭제할 수 없다")
    void 사용자는_없는_댓글을_삭제할_수_없다() {
        //given
        TestCommentContainer container = TestCommentContainer.builder()
                .datetimeHolder(() -> LocalDateTime.of(2023, 12, 31, 0, 0))
                .build();
        Long notFoundId = 9999999L;
        //when
        //then
        assertThatThrownBy(() -> container.commentController.delete(notFoundId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Comment에서 ID %d를 찾을 수 없습니다.", notFoundId);
    }
}
