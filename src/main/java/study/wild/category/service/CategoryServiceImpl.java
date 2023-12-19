package study.wild.category.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.category.controller.port.CategoryService;
import study.wild.category.domain.Category;
import study.wild.category.service.dto.CategoryCreate;
import study.wild.category.service.dto.CategoryUpdate;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.service.DatetimeHolder;

import java.util.List;

@Service
@Builder
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final DatetimeHolder datetimeHolder;

    @Override
    public Category getById(Long id) {
        return categoryRepository.getById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category create(CategoryCreate categoryCreate) {
        return categoryRepository.save(Category.fromCreate(categoryCreate));
    }

    @Override
    @Transactional
    public Category update(Long id, CategoryUpdate categoryUpdate) {
        Category category = getById(id);
        category = category.update(categoryUpdate);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        // TODO : Post가 있으면 삭제 못하게 에러 발생
        Category category = getById(id);
        category.delete(datetimeHolder.now());
        categoryRepository.save(category);
        return id;
    }
}
