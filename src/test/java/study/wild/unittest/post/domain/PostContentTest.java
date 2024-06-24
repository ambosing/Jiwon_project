package study.wild.unittest.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.post.domain.PostContent;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostContentTest {

    @Test
    @DisplayName("PostContent을 만들 수 있다")
    void PostContent을_만들_수_있다() {
        //given
        String content = "test";
        //when
        PostContent postTitle = PostContent.builder()
                .content(content)
                .build();
        //then
        assertThat(postTitle.content()).isEqualTo("test");
    }

    @Test
    @DisplayName("content에 Null이 들어오면 에러가 발생한다")
    void content에_Null이_들어오면_에러가_발생한다() {
        //given
        String content = null;
        //when
        //then
        assertThatThrownBy(() -> PostContent.builder().content(content).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용을 입력해주세요.");
    }

    @Test
    @DisplayName("content에 빈칸이 들어오면 에러가 발생한다")
    void content에_빈칸이_들어오면_에러가_발생한다() {
        //given
        String content = "";
        //when
        //then
        assertThatThrownBy(() -> PostContent.builder().content(content).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("내용을 입력해주세요.");
    }

}
