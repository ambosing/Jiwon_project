package study.wild.category.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CategoryUpdate {
    @NotBlank
    String name;
    LocalDateTime deleteDateTime;

    @Builder
    public CategoryUpdate(String name, LocalDateTime deleteDateTime) {
        this.name = name;
        this.deleteDateTime = deleteDateTime;
    }
}
