package study.wild.unittest.category.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.category.domain.CategoryName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryNameTest {

    @Test
    @DisplayName("카테고리 이름을 생성할 수 있다")
    void 카테고리_이름을_생성할_수_있다() throws Exception {
        //given
        String name = "createTest";
        //when
        CategoryName categoryName = CategoryName.builder()
                .name(name)
                .build();
        //then
        assertThat(categoryName).isNotNull();
        assertThat(categoryName.name()).isEqualTo(name);
    }

    @Test
    @DisplayName("빈 공백이나 NULL이면 카테고리 이름을 생성할 수 없다")
    void 빈_공백이나_Null_이면_카테고리_이름을_생성할_수_없다() throws Exception {
        //given
        String blankName = "";
        String nullName = null;
        //when
        //then
        assertThatThrownBy(() -> CategoryName.builder().name(blankName).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름을 입력해주세요.");
        assertThatThrownBy(() -> CategoryName.builder().name(nullName).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름을 입력해주세요.");
    }

}
