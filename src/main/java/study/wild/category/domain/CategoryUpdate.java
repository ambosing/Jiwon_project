package study.wild.category.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CategoryUpdate {
    @NotBlank(message = "이름을 입력해주세요.")
    String name;

    LocalDateTime deleteDateTime;

    @Builder
    public CategoryUpdate(String name, LocalDateTime deleteDateTime) {
        this.name = name;
        this.deleteDateTime = deleteDateTime;
    }
}
