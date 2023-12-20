package study.wild.post.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public final class PostCreate {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Long categoryId;

    @Builder
    public PostCreate(String title, String content, Long categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }

}
