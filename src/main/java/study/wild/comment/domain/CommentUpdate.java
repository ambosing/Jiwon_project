package study.wild.comment.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CommentUpdate(@NotNull Long postId, @NotEmpty String content) {

    @Builder
    public CommentUpdate(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
