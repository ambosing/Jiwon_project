package study.wild.category.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import study.wild.category.service.dto.CategoryCreate;
import study.wild.category.service.dto.CategoryUpdate;
import study.wild.post.domain.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Category {
    private Long id;
    private CategoryName name;
    private List<Post> post = new ArrayList<>();
    private LocalDateTime deleteDate;

    @Builder
    private Category(Long id, String name, LocalDateTime deleteDate) {
        this.id = id;
        this.name = new CategoryName(name);
        this.deleteDate = deleteDate;
    }

    public static Category fromCreate(CategoryCreate categoryCreate) {
        return Category.builder()
                .name(categoryCreate.getName())
                .build();
    }

    public Category update(CategoryUpdate categoryUpdate) {
        this.name = CategoryName.builder()
                .name(categoryUpdate.getName())
                .build();
        this.deleteDate = categoryUpdate.getDeleteDateTime();
        return this;
    }

    public void delete(LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }
}
