package study.wild.unittest.post.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.category.domain.Category;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.controller.port.PostService;
import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;
import study.wild.post.service.PostServiceImpl;
import study.wild.unittest.mock.category.FakeCategoryRepository;
import study.wild.unittest.mock.common.TestDateTimeHolder;
import study.wild.unittest.mock.post.FakePostRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostServiceTest {

    private PostService postService;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeCategoryRepository fakeCategoryRepository = new FakeCategoryRepository();

        postService = PostServiceImpl.builder()
                .postRepository(fakePostRepository)
                .categoryRepository(fakeCategoryRepository)
                .datetimeHolder(new TestDateTimeHolder(LocalDateTime.MAX))
                .build();
        Category category1 = Category.builder()
                .name("category1")
                .build();
        Category category2 = Category.builder()
                .name("category2")
                .build();
        fakeCategoryRepository.save(category1);
        fakeCategoryRepository.save(category2);
        
        fakePostRepository.save(
                Post.builder()
                        .view(1L)
                        .title("title1")
                        .content("content1")
                        .category(category1)
                        .build()
        );
        fakePostRepository.save(
                Post.builder()
                        .view(2L)
                        .title("title2")
                        .content("content2")
                        .category(category2)
                        .build()
        );
    }

    @Test
    @DisplayName("id로 Post를 조회할 수 있다.")
    void id로_Post_를_조회할_수_있다() {
        //given
        Long id = 1L;
        //when
        Post findPost = postService.getById(id);
        //then
        assertThat(findPost.getId()).isEqualTo(id);
        assertThat(findPost.getTitle()).isEqualTo("title1");
        assertThat(findPost.getContent()).isEqualTo("content1");
        assertThat(findPost.getCategory().getName().name()).isEqualTo("category1");
        assertThat(findPost.getView()).isEqualTo(1L);
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
        assertThat(savedPost.getId()).isEqualTo(3L);
        assertThat(savedPost.getTitle().title()).isEqualTo("createTitle");
        assertThat(savedPost.getContent().content()).isEqualTo("createContent");
        assertThat(savedPost.getCategory().getName().name()).isEqualTo("category1");
        assertThat(savedPost.getView()).isEqualTo(0L);
    }
}