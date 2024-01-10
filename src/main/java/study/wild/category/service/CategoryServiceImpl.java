package study.wild.category.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.category.controller.port.CategoryService;
import study.wild.category.domain.Category;
import study.wild.category.domain.CategoryCreate;
import study.wild.category.domain.CategoryUpdate;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.service.port.DatetimeHolder;

import java.util.List;

@Slf4j
@Service
@Builder
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final DatetimeHolder datetimeHolder;

    public Category getById(Long id) {
        return categoryRepository.getById(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category create(CategoryCreate categoryCreate) {
        return categoryRepository.save(Category.fromCreate(categoryCreate));
    }

    @Transactional
    public Category update(Long id, CategoryUpdate categoryUpdate) {
        Category category = getById(id);
        category = category.update(id, categoryUpdate);
        return categoryRepository.save(category);
    }

    @Transactional
    public Long delete(Long id) {
        // TODO : Post가 있으면 삭제 못하게 에러 발생
        Category category = getById(id);
        Category deletedCategory = category.delete(datetimeHolder.now());
        return categoryRepository.save(deletedCategory).getId();
    }
}
