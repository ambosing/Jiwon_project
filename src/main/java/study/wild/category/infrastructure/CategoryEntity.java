package study.wild.category.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.wild.category.domain.Category;
import study.wild.common.infrastructure.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private LocalDateTime deletedDate;

    @Builder
    private CategoryEntity(Long id, String name, LocalDateTime deletedDate) {
        this.id = id;
        this.name = name;
        this.deletedDate = deletedDate;
    }

    public static CategoryEntity from(Category category) {
        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName().name())
                .deletedDate(category.getDeletedDate())
                .build();
    }

    public Category toDomain() {
        return Category.builder()
                .id(id)
                .name(name)
                .build();
    }
}
