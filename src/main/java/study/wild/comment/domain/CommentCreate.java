package study.wild.comment.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreate {
    @NotNull
    private Long postId;

    @NotEmpty
    private String content;

    @Builder
    private CommentCreate(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
