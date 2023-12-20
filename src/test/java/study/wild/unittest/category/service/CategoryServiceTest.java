package study.wild.unittest.category.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.category.controller.port.CategoryService;
import study.wild.category.domain.Category;
import study.wild.category.domain.CategoryCreate;
import study.wild.category.domain.CategoryUpdate;
import study.wild.category.service.CategoryServiceImpl;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.unittest.mock.category.FakeCategoryRepository;
import study.wild.unittest.mock.common.TestDateTimeHolder;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoryServiceTest {

    private CategoryService categoryService;

    @BeforeEach
    void init() {
        FakeCategoryRepository fakeCategoryRepository = new FakeCategoryRepository();

        categoryService = CategoryServiceImpl.builder()
                .categoryRepository(fakeCategoryRepository)
                .datetimeHolder(new TestDateTimeHolder(LocalDateTime.MAX))
                .build();

        fakeCategoryRepository.save(
                Category.builder()
                        .name("test1")
                        .build()
        );
        fakeCategoryRepository.save(
                Category.builder()
                        .name("test2")
                        .build()
        );
    }

    @Test
    @DisplayName("카테고리를 모두 조회할 수 있다")
    void 카테고리를_모두_조회할_수_있다() throws Exception {
        //given
        //when
        List<Category> categories = categoryService.findAll();
        //then
        assertThat(categories).hasSize(2);
        assertThat(categories)
                .extracting(category -> category.getName().name())
                .containsExactlyInAnyOrder("test1", "test2");
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
