package study.wild.unittest.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.category.domain.Category;
import study.wild.category.infrastructure.CategoryEntity;
import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;
import study.wild.post.domain.PostUpdate;
import study.wild.post.infrastructure.PostEntity;

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

    @Test
    @DisplayName("PostUpdate로 Post로 변경할 수 있다")
    void PostUpdate로_Post로_변경할_수_있다() {
        //given
        Long updateId = 1L;
        PostUpdate postUpdate = PostUpdate.builder()
                .title("updateTitle")
                .content("updateContent")
                .categoryId(1L)
                .build();
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        //when
        Post updatedPost = post.update(updateId, postUpdate);
        //then
        assertThat(updatedPost.getId()).isEqualTo(1L);
        assertThat(updatedPost.getTitle().title()).isEqualTo("updateTitle");
        assertThat(updatedPost.getContent().content()).isEqualTo("updateContent");
    }

    @Test
    @DisplayName("PostEntity는 Post로 변경할 수 있다")
    void PostEntity는_Post로_변경할_수_있다() {
        //given
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("category")
                .build();
        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .title("title")
                .content("content")
                .category(categoryEntity)
                .build();
        //when
        Post post = postEntity.toDomain();
        //then
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getTitle().title()).isEqualTo("title");
        assertThat(post.getContent().content()).isEqualTo("content");
    }
}
