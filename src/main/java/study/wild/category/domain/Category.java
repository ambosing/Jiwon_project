package study.wild.category.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import study.wild.post.domain.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class Category {
    private Long id;
    private CategoryName name;
    private List<Post> post = new ArrayList<>();
    private LocalDateTime deletedDate;

    @Builder
    private Category(Long id, String name, LocalDateTime deletedDate) {
        this.id = id;
        this.name = new CategoryName(name);
        this.deletedDate = deletedDate;
    }

    public static Category fromCreate(CategoryCreate categoryCreate) {
        return Category.builder()
                .name(categoryCreate.getName())
                .build();
    }

    public Category update(Long id, CategoryUpdate categoryUpdate) {
        return Category.builder()
                .id(id)
                .name(categoryUpdate.getName())
                .deletedDate(categoryUpdate.deleteDateTime)
                .build();
    }

    public Category delete(LocalDateTime deleteDate) {
        return Category.builder()
                .id(id)
                .name(name.name())
                .deletedDate(deleteDate)
                .build();
    }
}
