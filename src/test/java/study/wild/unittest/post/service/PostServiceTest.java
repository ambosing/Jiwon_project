package study.wild.unittest.post.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.category.domain.Category;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.controller.port.PostService;
import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;
import study.wild.post.service.PostServiceImpl;
import study.wild.post.service.port.PostRepository;
import study.wild.unittest.mock.category.FakeCategoryRepository;
import study.wild.unittest.mock.post.FakePostRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

class PostServiceTest {
    private PostService postService;
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        postRepository = new FakePostRepository();
        categoryRepository = new FakeCategoryRepository();
        postService = PostServiceImpl.builder()
                .datetimeHolder(() -> LocalDateTime.of(2023, 12, 25, 0, 0))
                .postRepository(postRepository)
                .categoryRepository(categoryRepository)
                .build();
    }

    @Test
    @DisplayName("id로 Post를 조회할 수 있다.")
    void id로_Post_를_조회할_수_있다() {
        //given
        Long id = 1L;
        Category category1 = Category.builder()
                .name("category1")
                .build();
        categoryRepository.save(category1);
        postRepository.save(
                Post.builder()
                        .view(1L)
                        .title("title1")
                        .content("content1")
                        .category(category1)
                        .build()
        );
        //when
        Post findPost = postService.getById(id);
        //then
        assertThat(findPost.getId()).isEqualTo(id);
        assertThat(findPost.getTitle().title()).isEqualTo("title1");
        assertThat(findPost.getContent().content()).isEqualTo("content1");
        assertThat(findPost.getCategory().getName().name()).isEqualTo("category1");
        assertThat(findPost.getView()).isEqualTo(1L);
    }

    @Test
    @DisplayName("해당 카테고리에 맞는 Post들을 조회할 수 있다")
    void 해당_카테고리에_맞는_Post들을_조회할_수_있다() {
        //given
        Category category = Category.builder()
                .name("category1")
                .build();
        Category category1 = categoryRepository.save(category);

        Post post1 = Post.builder()
                .title("title1")
                .content("content1")
                .category(category1)
                .build();
        Post post2 = Post.builder()
                .title("title2")
                .content("content2")
                .category(category1)
                .build();
        postRepository.save(post1);
        postRepository.save(post2);
        //when
        List<Post> posts = postService.getByCategoryId(category1.getId());
        //then
        assertThat(posts).hasSize(2)
                .extracting(post -> post.getTitle().title(), post -> post.getContent().content())
                .containsExactlyInAnyOrder(
                        tuple("title1", "content1"),
                        tuple("title2", "content2")
                );
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
        Post post = postRepository.save(
                Post.builder()
                        .view(1L)
                        .title("title1")
                        .content("content1")
                        .category(category1)
                        .deletedDate(LocalDateTime.of(2023, 12, 25, 0, 0))
                        .build()
        );
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
        Category category1 = Category.builder()
                .name("category1")
                .build();
        Category category = categoryRepository.save(category1);
        PostCreate postCreate = PostCreate.builder()
                .title("createTitle")
                .content("createContent")
                .categoryId(category.getId())
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
        categoryRepository.save(category1);
        Post savedPost = postRepository.save(
                Post.builder()
                        .view(1L)
                        .title("title1")
                        .content("content1")
                        .category(category1)
                        .build()
        );
        //when
        Long deletedId = postService.delete(savedPost.getId());
        //then
        assertThat(deletedId).isEqualTo(savedPost.getId());
    }
}