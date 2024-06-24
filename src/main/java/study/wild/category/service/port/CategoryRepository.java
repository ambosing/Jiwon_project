package study.wild.category.service.port;

import study.wild.category.domain.Category;

import java.util.List;

public interface CategoryRepository {

    Category save(Category category);

    Category getById(Long id);

    List<Category> findAll();
}
