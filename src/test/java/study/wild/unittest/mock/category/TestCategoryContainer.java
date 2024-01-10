package study.wild.unittest.mock.category;

import lombok.Builder;
import study.wild.category.controller.CategoryController;
import study.wild.category.controller.port.CategoryService;
import study.wild.category.service.CategoryServiceImpl;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.service.port.DatetimeHolder;

public class TestCategoryContainer {
    public final CategoryRepository categoryRepository;
    public final CategoryService categoryService;
    public final CategoryController categoryController;

    @Builder
    public TestCategoryContainer(DatetimeHolder datetimeHolder) {
        this.categoryRepository = new FakeCategoryRepository();
        this.categoryService = CategoryServiceImpl.builder()
                .categoryRepository(this.categoryRepository)
                .datetimeHolder(datetimeHolder)
                .build();
        this.categoryController = CategoryController.builder()
                .categoryService(categoryService)
                .build();
    }
}
