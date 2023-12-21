package study.wild.unittest.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.category.domain.Category;
import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    @DisplayName("PostCreate는 Post로 변경할 수 있다")
    void PostCreate는_Post로_변경할_수_있다() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("title")
                .content("content")
                .categoryId(1L)
                .build();

        Category category = Category.builder()
                .name("category")
                .build();
        //when
        Post post = Post.fromCreate(category, postCreate);
        //then
        assertThat(post).isNotNull();
        assertThat(post.getTitle().title()).isEqualTo("title");
        assertThat(post.getContent().content()).isEqualTo("content");
        assertThat(post.getCategory().getName().name()).isEqualTo("category");
        assertThat(post.getView()).isEqualTo(0L);
    }
}
