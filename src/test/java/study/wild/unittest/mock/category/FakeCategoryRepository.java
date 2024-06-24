package study.wild.unittest.mock.category;

import study.wild.category.domain.Category;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.domain.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeCategoryRepository implements CategoryRepository {

    private Long id = 0L;
    private final List<Category> data = new ArrayList<>();


    @Override
    public Category save(Category category) {
        if (category.getId() == null || category.getId() == 0) {
            Category newCategory = Category.builder()
                    .id(++id)
                    .name(category.getName().name())
                    .build();
            data.add(newCategory);
            return newCategory;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), category.getId()));
            data.add(category);
            return category;
        }
    }

    @Override
    public Category getById(Long id) {
        return data.stream()
                .filter(item -> item.getId().equals(id) && item.getDeletedDate() == null)
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    @Override
    public List<Category> findAll() {
        return data;
    }

}
