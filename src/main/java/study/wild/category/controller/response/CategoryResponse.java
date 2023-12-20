package study.wild.category.controller.response;

import lombok.Builder;
import lombok.Getter;
import study.wild.category.domain.Category;

@Builder
@Getter
public class CategoryResponse {
    private Long id;
    private String name;

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName().name())
                .build();
    }
}
