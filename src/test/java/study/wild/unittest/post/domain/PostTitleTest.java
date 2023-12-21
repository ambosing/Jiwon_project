package study.wild.unittest.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.post.domain.PostTitle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostTitleTest {
    @Test
    @DisplayName("PostTitle을 만들 수 있다")
    void PostTitle을_만들_수_있다() {
        //given
        String title = "test";
        //when
        PostTitle postTitle = PostTitle.builder()
                .title(title)
                .build();
        //then
        assertThat(postTitle.title()).isEqualTo("test");
    }

    @Test
    @DisplayName("title에 Null이 들어오면 에러가 발생한다")
    void title에_Null이_들어오면_에러가_발생한다() {
        //given
        String title = null;
        //when
        //then
        assertThatThrownBy(() -> PostTitle.builder().title(title).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목을 입력해주세요.");
    }

    @Test
    @DisplayName("title에 빈칸이 들어오면 에러가 발생한다")
    void title에_빈칸이_들어오면_에러가_발생한다() {
        //given
        String title = "";
        //when
        //then
        assertThatThrownBy(() -> PostTitle.builder().title(title).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목을 입력해주세요.");
    }
}
