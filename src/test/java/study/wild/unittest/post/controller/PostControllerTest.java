package study.wild.unittest.post.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import study.wild.category.domain.Category;
import study.wild.post.controller.response.PostResponse;
import study.wild.post.domain.Post;
import study.wild.unittest.mock.category.FakeCategoryRepository;
import study.wild.unittest.mock.post.TestPostContainer;

import java.time.LocalDateTime;

class PostControllerTest {

    @Test
    @DisplayName("사용자는 게시물을 단건 조회할 수 있다")
    void 사용자는_게시물을_단건_조회할_수_있다() {
        //given
        TestPostContainer container = TestPostContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();
        FakeCategoryRepository fakeCategoryRepository = new FakeCategoryRepository();

        Category category = Category.builder()
                .name("category")
                .build();
        fakeCategoryRepository.save(category);
        container.postRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .category(category)
                        .build());
        //when
        ResponseEntity<PostResponse> result = container.postController.getById(1L);
        //then

    }
}
