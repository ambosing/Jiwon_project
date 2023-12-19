package study.wild.category.controller.port;

import study.wild.category.domain.Category;
import study.wild.category.domain.CategoryCreate;
import study.wild.category.domain.CategoryUpdate;

import java.util.List;

public interface CategoryService {
    // get : No-Optional / find : Optional
    Category create(CategoryCreate categoryCreate);

    Category getById(Long id);

    List<Category> findAll();

    Category update(Long id, CategoryUpdate categoryUpdate);

    Long delete(Long id);
}
