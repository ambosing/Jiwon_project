package study.wild.integrationtest.post.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import study.wild.category.domain.Category;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.controller.port.PostService;
import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;
import study.wild.post.service.port.PostRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @DisplayName("삭제된 게시물을 조회하면 에러가 발생한다")
    void 삭제된_게시물을_조회하면_에러가_발생한다() {
        //given
        Category category1 = Category.builder()
                .name("category1")
                .build();
        categoryRepository.save(category1);
        Post createPost = Post.builder()
                .view(1L)
                .title("title1")
                .content("content1")
                .category(category1)
                .deletedDate(LocalDateTime.of(2023, 12, 25, 0, 0))
                .build();
        Post post = postRepository.save(createPost);
        Post savedPost = postRepository.save(post);
        //when
        //then
        assertThatThrownBy(() -> postService.getById(savedPost.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Post에서 ID 1를 찾을 수 없습니다.");
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
}
