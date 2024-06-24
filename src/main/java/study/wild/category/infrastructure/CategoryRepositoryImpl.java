package study.wild.category.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import study.wild.category.domain.Category;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.domain.ResourceNotFoundException;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category getById(Long id) {
        return categoryJpaRepository
                .findByIdAndDeletedDateIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id)).toDomain();
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository
                .findAllByDeletedDateIsNull()
                .stream().map(CategoryEntity::toDomain)
                .toList();
    }

    @Override
    public Category save(Category category) {
        return categoryJpaRepository
                .save(CategoryEntity.from(category))
                .toDomain();
    }
}
