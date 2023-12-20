package study.wild.unittest.category.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.category.domain.Category;
import study.wild.category.domain.CategoryCreate;
import study.wild.category.domain.CategoryUpdate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {
    @Test
    @DisplayName("CategoryCreate로 카테고리를 만들 수 있다")
    void categoryCreate_로_카테고리를_만들_수_있다() throws Exception {
        //given
        CategoryCreate createTest = CategoryCreate.builder()
                .name("CreateTest")
                .build();
        //when
        Category category = Category.fromCreate(createTest);

        //then
        assertThat(category.getName().name()).isEqualTo("CreateTest");
    }

    @Test
    @DisplayName("CategoryUpdate로 변경된 카테고리를 만들 수 있다")
    void categoryUpdate_로_변경된_카테고리를_만들_수_있다() throws Exception {
        //given
        CategoryUpdate updateTest = CategoryUpdate.builder()
                .name("UpdateTest")
                .build();
        Category preUpdate = Category.builder()
                .id(30L)
                .name("PreUpdate")
                .build();
        //when
        Category updatedCategory = preUpdate.update(30L, updateTest);
        //then
        assertThat(updatedCategory.getId()).isEqualTo(30L);
        assertThat(updatedCategory.getName().name()).isEqualTo("UpdateTest");
    }

    @Test
    @DisplayName("delete로 삭제된 날짜가 설정된 카테고리를 만들 수 있다")
    void delete_로_삭제된_날짜를_설정된_카테고리를_만들_수_있다() throws Exception {
        //given
        Category deleteTest = Category.builder()
                .id(30L)
                .name("PreDelete")
                .build();
        LocalDateTime deleteDate = LocalDateTime.now();
        //when
        deleteTest.delete(deleteDate);
        //then
        assertThat(deleteTest.getId()).isEqualTo(30L);
        assertThat(deleteTest.getName().name()).isEqualTo("PreDelete");
        assertThat(deleteTest.getDeletedDate().compareTo(deleteDate)).isZero();
    }
}
