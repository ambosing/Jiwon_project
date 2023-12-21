package study.wild.unittest.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.category.domain.Category;
import study.wild.post.controller.response.PostResponse;
import study.wild.post.domain.Post;

import static org.assertj.core.api.Assertions.assertThat;

class PostResponseTest {

    @Test
    @DisplayName("Post를 PostResponse로 변경할 수 있다")
    void Post를_PostResponse로_변경할_수_있다() {
        //given

        Category category = Category.builder()
                .name("category")
                .build();

        Post post = Post.builder()
                .title("title")
                .content("content")
                .id(1L)
                .view(1L)
                .category(category)
                .build();
        //when
        PostResponse response = PostResponse.from(post);
        //then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isEqualTo("content");
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getView()).isEqualTo(1L);
        assertThat(response.getCategory().getName()).isEqualTo("category");
    }
}
