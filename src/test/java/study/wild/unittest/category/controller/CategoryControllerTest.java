package study.wild.unittest.category.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import study.wild.category.controller.response.CategoryResponse;
import study.wild.category.domain.Category;
import study.wild.category.domain.CategoryCreate;
import study.wild.category.domain.CategoryUpdate;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.unittest.mock.category.TestCategoryContainer;
import study.wild.unittest.mock.common.TestDateTimeHolder;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CategoryControllerTest {
    @Test
    @DisplayName("사용자는 모든 카테고리를 조회할 수 있다")
    void 사용자는_모든_카테고리를_조회할_수_있다() throws Exception {
        //given
        TestCategoryContainer container = TestCategoryContainer.builder().build();
        container.categoryRepository.save(Category.builder()
                .name("category1")
                .build());
        container.categoryRepository.save(Category.builder()
                .name("category2")
                .build());
        //when
        ResponseEntity<List<CategoryResponse>> results = container.categoryController.findAll();
        //then
        assertThat(results.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(results.getBody()).hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder("category1", "category2");
    }

    @Test
    @DisplayName("사용자는 카테고리를 단건 조회할 수 있다")
    void 사용자는_카테고리를_단건_조회할_수_있다() throws Exception {
        //given
        TestCategoryContainer container = TestCategoryContainer.builder().build();
        container.categoryRepository.save(Category.builder()
                .name("category1")
                .build());
        container.categoryRepository.save(Category.builder()
                .name("category2")
                .build());
        //when
        ResponseEntity<CategoryResponse> result = container.categoryController.getById(1L);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody().getName()).isEqualTo("category1");
    }

    @Test
    @DisplayName("사용자는 없는 카테고리를 조회할 경우 에러가 발생한다")
    void 사용자는_없는_카테고리를_조회할_경우_에러가_발생한다() throws Exception {
        //given
        TestCategoryContainer container = TestCategoryContainer.builder().build();
        //when
        //then
        assertThatThrownBy(() -> container.categoryController.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category에서 ID 1를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("사용자는 삭제된 카테고리를 조회할 경우 에러에 마주한다")
    void 사용자는_삭제된_카테고리를_조회할_경우_에러에_마주한다() {
        //given
        TestCategoryContainer container = TestCategoryContainer.builder()
                .datetimeHolder(LocalDateTime::now)
                .build();
        container.categoryRepository.save(Category.builder()
                .name("category")
                .build()
        );
        container.categoryService.delete(1L);
        //when
        //then
        assertThatThrownBy(() -> container.categoryController.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category에서 ID 1를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("사용자는 카테고리를 생성할 수 있다")
    void 사용자는_카테고리를_생성할_수_있다() {
        //given
        TestCategoryContainer container = TestCategoryContainer.builder().build();
        CategoryCreate createTest = CategoryCreate.builder()
                .name("CreateTest")
                .build();
        //when
        ResponseEntity<CategoryResponse> result = container.categoryController.create(createTest);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).extracting(CategoryResponse::getName)
                .isEqualTo("CreateTest");
    }

    @Test
    @DisplayName("사용자는 카테고리를 수정할 수 있다")
    void 사용자는_카테고리를_수정할_수_있다() {
        //given
        TestCategoryContainer container = TestCategoryContainer.builder().build();
        CategoryUpdate updateTest = CategoryUpdate.builder()
                .name("UpdateTest")
                .build();
        Category preUpdate = Category.builder()
                .name("PreUpdate")
                .build();
        Category savedCategory = container.categoryRepository.save(preUpdate);
        //when
        ResponseEntity<CategoryResponse> result = container.categoryController.update(savedCategory.getId(), updateTest);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody().getName()).isEqualTo("UpdateTest");
    }

    @Test
    @DisplayName("사용자는 카테고리 이름을 빈칸이나 NULL로 변경할 수 없다")
    void 사용자는_카테고리_이름을_빈칸이나_NULL로_변경할_수_없다() {
        //given
        TestCategoryContainer container = TestCategoryContainer.builder().build();
        CategoryUpdate updateTest1 = CategoryUpdate.builder()
                .name("")
                .build();
        CategoryUpdate updateTest2 = CategoryUpdate.builder()
                .name(null)
                .build();
        Category preUpdate = Category.builder()
                .name("PreUpdate")
                .build();
        Category savedCategory = container.categoryRepository.save(preUpdate);
        //when
        //then
        assertAll(
                () -> assertThatThrownBy(() -> container.categoryController.update(savedCategory.getId(), updateTest1))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이름을 입력해주세요."),
                () -> assertThatThrownBy(() -> container.categoryController.update(savedCategory.getId(), updateTest2))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이름을 입력해주세요.")
        );
    }

    @Test
    @DisplayName("사용자는_카테고리를 삭제할 수 있다")
    void 사용자는_카테고리를_삭제할_수_있다() {
        //given
        TestCategoryContainer container = TestCategoryContainer.builder()
                .datetimeHolder(new TestDateTimeHolder(LocalDateTime.MAX))
                .build();
        Category category = Category.builder()
                .name("PreUpdate")
                .build();
        Category savedCategory = container.categoryRepository.save(category);
        //when
        ResponseEntity<Long> result = container.categoryController.delete(savedCategory.getId());
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isEqualTo(1L);
    }
}
