package study.wild.category.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryCreate {
    @NotBlank
    String name;

    @Builder
    private CategoryCreate(String name) {
        this.name = name;
    }
}
