package study.wild.category.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.wild.category.domain.Category;
import study.wild.common.infrastructure.BaseTimeEntity;
import study.wild.post.infrastructure.PostEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private final List<PostEntity> post = new ArrayList<>();

    @Column
    private LocalDateTime deleteDate;

    @Builder
    private CategoryEntity(Long id, String name, LocalDateTime deleteDate) {
        this.id = id;
        this.name = name;
        this.deleteDate = deleteDate;
    }

    public static CategoryEntity from(Category category) {
        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName().name())
                .build();
    }

    public Category toDomain() {
        return Category.builder()
                .id(id)
                .name(name)
                .build();
    }
}
