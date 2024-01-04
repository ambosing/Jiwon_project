package study.wild.unittest.post.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.wild.category.domain.Category;
import study.wild.comment.domain.Comment;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.controller.response.PostListResponse;
import study.wild.post.controller.response.PostResponse;
import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;
import study.wild.post.domain.PostUpdate;
import study.wild.unittest.mock.post.TestPostContainer;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostControllerTest {

    @Test
    @DisplayName("사용자는 게시물을 단건 조회할 수 있다")
    void 사용자는_게시물을_단건_조회할_수_있다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();

        Category category = Category.builder()
                .name("category")
                .build();
        container.categoryRepository.save(category);
        container.postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .category(category)
                        .view(0L)
                        .build());
        //when
        ResponseEntity<PostResponse> result = container.postController.getById(1L);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody().getCategory().getName()).isEqualTo("category");
        assertThat(result.getBody().getTitle()).isEqualTo("title");
        assertThat(result.getBody().getContent()).isEqualTo("content");
        assertThat(result.getBody().getView()).isEqualTo(0L);
    }

    @Test
    @DisplayName("사용자는 게시물과 함께 댓글을 조회할 수 있다")
    void 사용자는_게시물과_함께_댓글을_조회할_수_있다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
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
        Comment comment1 = Comment.builder()
                .id(1L)
                .post(savedPost)
                .content("comment1")
                .build();
        Comment comment2 = Comment.builder()
                .id(2L)
                .post(savedPost)
                .content("comment2")
                .build();
        container.commentRepository.save(comment1);
        container.commentRepository.save(comment2);
        //when
        ResponseEntity<PostResponse> result = container.postController.getWithCommentById(1L);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getBody().getComments().getCommentListResponse().get(0).getContent()).isEqualTo("comment1");
        assertThat(result.getBody().getComments().getCommentListResponse().get(1).getContent()).isEqualTo("comment2");
        assertThat(result.getBody().getComments().getCommentListResponse().get(0).getId()).isEqualTo(1L);
        assertThat(result.getBody().getComments().getCommentListResponse().get(1).getId()).isEqualTo(2L);
        assertThat(result.getBody().getTitle()).isEqualTo("title1");
        assertThat(result.getBody().getContent()).isEqualTo("content1");
        assertThat(result.getBody().getView()).isEqualTo(1L);
    }

    @Test
    @DisplayName("사용자는 카테고리에 맞는 게시물들을 조회할 수 있다")
    void 사용자는_카테고리에_맞는_게시물들을_조회할_수_있다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();

        Category category = Category.builder()
                .name("category")
                .build();
        container.categoryRepository.save(category);
        container.postRepository.save(
                Post.builder()
                        .title("title1")
                        .content("content1")
                        .category(category)
                        .view(0L)
                        .build());
        container.postRepository.save(
                Post.builder()
                        .title("title2")
                        .content("content2")
                        .category(category)
                        .view(0L)
                        .build());
        //when
        ResponseEntity<Page<PostListResponse>> results = container.postController.getByCategoryId(category.getId(), null, PageRequest.of(0, 10));
        //then
        assertThat(results.getBody()).hasSize(2)
                .extracting(PostListResponse::getTitle, PostListResponse::getContent)
                .containsExactlyInAnyOrder(
                        tuple("title1", "content1"),
                        tuple("title2", "content2")
                );
    }

    @Test
    @DisplayName("사용자는 게시물을 페이징해서 볼 수 있다")
    void 사용자는_게시물을_페이징해서_볼_수_있다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();

        Category category = Category.builder()
                .name("category")
                .build();
        container.categoryRepository.save(category);
        container.postRepository.save(
                Post.builder()
                        .title("title1")
                        .content("content1")
                        .category(category)
                        .view(0L)
                        .build());
        container.postRepository.save(
                Post.builder()
                        .title("title2")
                        .content("content2")
                        .category(category)
                        .view(0L)
                        .build());
        //when
        ResponseEntity<Page<PostListResponse>> results1 = container.postController.getByCategoryId(category.getId(), null, PageRequest.of(0, 1));
        ResponseEntity<Page<PostListResponse>> results2 = container.postController.getByCategoryId(category.getId(), null, PageRequest.of(1, 1));

        //then
        assertAll(
                () -> assertThat(results1.getBody().getContent().get(0).getId()).isNotNull(),
                () -> assertThat(results1.getBody().getContent().get(0).getTitle()).isEqualTo("title2"),
                () -> assertThat(results1.getBody().getContent().get(0).getContent()).isEqualTo("content2"),
                () -> assertThat(results1.getBody().getContent().get(0).getView()).isEqualTo(0),
                () -> assertThat(results2.getBody().getContent().get(0).getId()).isNotNull(),
                () -> assertThat(results2.getBody().getContent().get(0).getTitle()).isEqualTo("title1"),
                () -> assertThat(results2.getBody().getContent().get(0).getContent()).isEqualTo("content1"),
                () -> assertThat(results2.getBody().getContent().get(0).getView()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("사용자는 없는 게시물 번호를 조회할 경우 에러가 발생한다")
    void 사용자는_없는_게시물_번호를_조회할_경우_에러가_발생한다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();
        //when
        //then
        assertThatThrownBy(() -> container.postController.getById(123456789L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Post에서 ID 123456789를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("사용자는 새로운 게시물을 생성할 수 있다")
    void 사용자는_새로운_게시물을_생성할_수_있다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();
        Category category = Category.builder()
                .name("category")
                .build();
        container.categoryRepository.save(category);
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("content")
                .categoryId(1L)
                .build();
        //when
        ResponseEntity<PostResponse> result = container.postController.create(postCreate);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(result.getBody().getView()).isEqualTo(0L);
        assertThat(result.getBody().getCategory().getName()).isEqualTo("category");
        assertThat(result.getBody().getTitle()).isEqualTo("title");
        assertThat(result.getBody().getContent()).isEqualTo("content");
    }

    @Test
    @DisplayName("사용자는 새로운 게시물을 생성할 때 제목을 입력을 하지 않으면 오류에 마주한다")
    void 사용자는_새로운_게시물을_생성할_때_제목을_입력을_하지_않으면_오류에_마주한다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();
        Category category = Category.builder()
                .name("category")
                .build();
        container.categoryRepository.save(category);
        PostCreate postCreate = PostCreate.builder()
                .content("content")
                .categoryId(1L)
                .build();
        //when
        //then
        assertThatThrownBy(() -> container.postController.create(postCreate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목을 입력해주세요.");
    }

    @Test
    @DisplayName("사용자는 새로운 게시물을 생성할 때 내용을 입력하지 않으면 오류에 마주한다")
    void 사용자는_새로운_게시물을_생성할_때_내용을_입력하지_않으면_오류에_마주한다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();
        Category category = Category.builder()
                .name("category")
                .build();
        container.categoryRepository.save(category);
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .categoryId(1L)
                .build();
        //when
        //then
        assertThatThrownBy(() -> container.postController.create(postCreate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용을 입력해주세요.");
    }

    @Test
    @DisplayName("사용자는 게시물을 수정할 수 있다")
    void 사용자는_게시물을_수정할_수_있다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();
        Category category = Category.builder()
                .name("category")
                .build();
        container.categoryRepository.save(category);
        Post post = Post.builder()
                .title("title")
                .content("content")
                .category(category)
                .build();
        Post savedPost = container.postRepository.save(post);
        PostUpdate postUpdate = PostUpdate.builder()
                .title("updateTitle")
                .content("updateContent")
                .build();
        //when
        ResponseEntity<PostResponse> result = container.postController.update(savedPost.getId(), postUpdate);
        //then
        assertThat(result.getBody().getId()).isEqualTo(savedPost.getId());
        assertThat(result.getBody().getTitle()).isEqualTo(postUpdate.getTitle());
        assertThat(result.getBody().getContent()).isEqualTo(postUpdate.getContent());
    }

    @Test
    @DisplayName("사용자는 게시물을 삭제할 수 있다")
    void 사용자는_게시물을_삭제할_수_있다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();
        Category category = Category.builder()
                .name("category")
                .build();
        container.categoryRepository.save(category);
        Post post = Post.builder()
                .title("title")
                .content("content")
                .category(category)
                .build();
        Post savedPost = container.postRepository.save(post);
        //when
        ResponseEntity<Long> result = container.postController.delete(savedPost.getId());
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isEqualTo(savedPost.getId());
    }
}
