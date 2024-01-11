package study.wild.comment.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CommentUpdate(@NotNull Long postId, @NotEmpty String content, @NotNull Long userNo) {

    @Builder
    public CommentUpdate(Long postId, String content, Long userNo) {
        this.postId = postId;
        this.content = content;
        this.userNo = userNo;
    }
}
