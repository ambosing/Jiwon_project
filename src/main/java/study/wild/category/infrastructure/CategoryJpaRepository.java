package study.wild.category.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByIdAndDeletedDateIsNull(Long id);

    List<CategoryEntity> findAllByDeletedDateIsNull();
}
