package study.wild.integrationtest.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import study.wild.category.domain.Category;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.controller.port.PostService;
import study.wild.post.controller.response.PostListResponse;
import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;
import study.wild.post.domain.PostUpdate;
import study.wild.post.service.port.PostRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/post/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("id로 Post를 조회할 수 있다.")
    void id로_Post_를_조회할_수_있다() {
        //given
        Long id = 1L;
        //when
        Post findPost = postService.getById(id);
        //then
        assertThat(findPost.getId()).isEqualTo(id);
        assertThat(findPost.getTitle().title()).isEqualTo("title1");
        assertThat(findPost.getContent().content()).isEqualTo("content1");
        assertThat(findPost.getCategory().getName().name()).isEqualTo("category1");
        assertThat(findPost.getView()).isEqualTo(0L);
    }

    @Test
    @DisplayName("id로 없는 Post를 조회할 경우 에러가 발생한다")
    void id로_없는_Post를_조회할_경우_에러가_발생한다() {
        //given
        Long id = 123456789L;
        //when
        //then
        assertThatThrownBy(() -> postService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Post에서 ID 123456789를 찾을 수 없습니다.");
    }


    @Test
    @DisplayName("해당 카테고리에 맞는 Post들을 조회할 수 있다")
    void 해당_카테고리에_맞는_Post들을_조회할_수_있다() {
        //given
        Category category = Category.builder()
                .name("category1")
                .build();

        Category category1 = categoryRepository.save(category);

        Post post1 = Post.fromCreate(category1, PostCreate.builder()
                .title("title1")
                .content("content1")
                .categoryId(category1.getId())
                .build());
        Post post2 = Post.fromCreate(category1, PostCreate.builder()
                .title("title2")
                .content("content2")
                .categoryId(category1.getId())
                .build());
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        Page<PostListResponse> posts = postService.getByCategoryId(category1.getId(), null, PageRequest.of(0, 10));
        //then
        assertThat(posts).hasSize(2)
                .extracting(PostListResponse::getTitle, PostListResponse::getContent)
                .containsExactlyInAnyOrder(
                        tuple("title1", "content1"),
                        tuple("title2", "content2")
                );
    }

    @Test
    @DisplayName("카테고리가 null인 경우 모든 게시물들이 조회된다")
    void 카테고리가_null인_경우_모든_게시물들이_조회된다() {
        //given
        Category category1 = Category.builder()
                .name("category1")
                .build();
        Category savedCategory1 = categoryRepository.save(category1);
        Category category2 = Category.builder()
                .name("category2")
                .build();
        Category savedCategory2 = categoryRepository.save(category2);

        Post post1 = Post.builder()
                .title("title3")
                .content("content3")
                .category(savedCategory1)
                .build();
        Post post2 = Post.builder()
                .title("title4")
                .content("content4")
                .category(savedCategory2)
                .build();
        postRepository.save(post1);
        postRepository.save(post2);
        //when
        Page<PostListResponse> posts = postService.getByCategoryId(null, null, PageRequest.of(0, 10));
        //then
        assertThat(posts).hasSize(4)
                .extracting(PostListResponse::getTitle, PostListResponse::getContent)
                .containsExactlyInAnyOrder(
                        tuple("title1", "content1"),
                        tuple("title2", "content2"),
                        tuple("title3", "content3"),
                        tuple("title4", "content4")
                );
    }

    @Test
    @DisplayName("삭제된 게시물을 조회하면 에러가 발생한다")
    void 삭제된_게시물을_조회하면_에러가_발생한다() {
        //given
        Category category1 = Category.builder()
                .name("category1")
                .build();
        Category category = categoryRepository.save(category1);
        Post post = Post.builder()
                .view(1L)
                .title("title1")
                .content("content1")
                .category(category)
                .deletedDate(LocalDateTime.of(2023, 12, 25, 0, 0))
                .build();
        Post savedPost = postRepository.save(post);
        //when
        //then
        assertThatThrownBy(() -> postService.getById(savedPost.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Post에서 ID %d를 찾을 수 없습니다.", savedPost.getId());
    }

    @Test
    @DisplayName("postCreate로 새로운 Post를 생성할 수 있다")
    void postCreate로_새로운_Post를_생성할_수_있다() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("createTitle")
                .content("createContent")
                .categoryId(1L)
                .build();
        //when
        Post savedPost = postService.create(postCreate);
        //then
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getTitle().title()).isEqualTo("createTitle");
        assertThat(savedPost.getContent().content()).isEqualTo("createContent");
        assertThat(savedPost.getCategory().getName().name()).isEqualTo("category1");
        assertThat(savedPost.getView()).isEqualTo(0L);
    }

    @Test
    @DisplayName("사용자는 게시물을 삭제할 수 있다")
    void 사용자는_게시물을_삭제할_수_있다() {
        //given
        Category category1 = Category.builder()
                .name("category1")
                .build();
        Category category = categoryRepository.save(category1);
        Post savedPost = postRepository.save(
                Post.builder()
                        .view(1L)
                        .title("title1")
                        .content("content1")
                        .category(category)
                        .build()
        );
        //when
        Long deletedId = postService.delete(savedPost.getId());
        //then
        assertThat(deletedId).isEqualTo(savedPost.getId());
    }

    @Test
    @DisplayName("Post를 수정할 수 있다")
    void Post를_수정할_수_있다() {
        //given
        Category category1 = Category.builder()
                .name("category1")
                .build();
        Category category = categoryRepository.save(category1);
        Post savedPost = postRepository.save(
                Post.builder()
                        .view(1L)
                        .title("title1")
                        .content("content1")
                        .category(category)
                        .build()
        );
        PostUpdate postUpdate = PostUpdate.builder()
                .title("UpdatedTitle")
                .content("UpdatedContent")
                .build();
        //when
        Post updatedPost = postService.update(savedPost.getId(), postUpdate);
        //then
        assertThat(updatedPost.getTitle().title()).isEqualTo("UpdatedTitle");
        assertThat(updatedPost.getContent().content()).isEqualTo("UpdatedContent");
        assertThat(updatedPost.getCategory()).isEqualTo(category);
    }
}
