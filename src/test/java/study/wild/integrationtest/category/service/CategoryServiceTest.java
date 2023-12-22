package study.wild.integrationtest.category.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import study.wild.category.controller.port.CategoryService;
import study.wild.category.domain.Category;
import study.wild.category.domain.CategoryCreate;
import study.wild.category.domain.CategoryUpdate;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.domain.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/category/category-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리를 모두 조회할 수 있다")
    void 카테고리를_모두_조회할_수_있다() {
        //given
        //when
        List<Category> results = categoryService.findAll();
        //then
        assertThat(results).hasSize(2)
                .extracting(category -> category.getName().name(), Category::getDeletedDate)
                .containsExactlyInAnyOrder(
                        tuple("category1", null),
                        tuple("category2", null)
                );
    }

    @Test
    @DisplayName("id로 카테고리를 가져올 수 있다")
    void id로_카테고리_를_가져올_수_있다() throws Exception {
        //given
        //when
        Category category = categoryService.getById(1L);
        //then
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("없는 id로 카테고리를 가져올 수 없다")
    void 없는_id로_카테고리를_가져올_수_없다() throws Exception {
        //given
        Long id = 123456789L;
        //when
        //then
        assertThatThrownBy(() -> categoryService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category에서 ID 123456789를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("삭제된 카테고리는 가져올 수 없다")
    void 삭제된_카테고리는_가져올_수_없다() {
        //give
        long deletedId = 1L;
        Category deletedCategory = Category.builder()
                .id(deletedId)
                .name("deletedCategory")
                .deletedDate(LocalDateTime.now())
                .build();
        Category category = categoryRepository.save(deletedCategory);
        //when
        //then
        assertThatThrownBy(() -> categoryService.getById(category.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(String.format("Category에서 ID %s를 찾을 수 없습니다.", deletedId));
    }

    @Test
    @DisplayName("CategoryCreate를 통해 카테고리를 생성할 수 있다")
    void categoryCreate_를_통해_카테고리를_생성할_수_있다() throws Exception {
        //given
        CategoryCreate createTest = CategoryCreate.builder()
                .name("CreateTest")
                .build();
        //when
        Category category = categoryService.create(createTest);

        //then
        assertThat(category).isNotNull();
        assertThat(category.getName().name()).isEqualTo("CreateTest");
    }

    @Test
    @DisplayName("CategoryUpdate를 통해 카테고리를 수정할 수 있다")
    void categoryUpdate_를_통해_카테고리를_수정할_수_있다() throws Exception {
        //given
        CategoryUpdate updateTest = CategoryUpdate.builder()
                .name("UpdateTest")
                .build();
        //when
        Category category = categoryService.update(2L, updateTest);
        //then
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(2L);
        assertThat(category.getName().name()).isEqualTo("UpdateTest");
    }

    @Test
    @DisplayName("없는 id를 수정하려는 경우 에러가 발생한다")
    void 없는_Id를_수정하려는_경우_에러가_발생한다() throws Exception {
        //given
        CategoryUpdate updateTest = CategoryUpdate.builder()
                .name("UpdateTest")
                .build();
        Long id = 123456789L;

        //when
        //then
        assertThatThrownBy(() -> categoryService.update(id, updateTest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category에서 ID 123456789를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("delete를 통해 카테고리를 지울 수 있다")
    void delete를_통해_카테고리를_지울_수_있다() throws Exception {
        //given
        //when
        categoryService.delete(1L);
        //then
        assertThatThrownBy(() -> categoryService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category에서 ID 1를 찾을 수 없습니다.");
    }
}
