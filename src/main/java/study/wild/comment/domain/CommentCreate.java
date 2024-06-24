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
    @NotNull
    private Long userNo;

    @NotEmpty
    private String content;

    @Builder
    public CommentCreate(Long postId, Long userNo, String content) {
        this.postId = postId;
        this.userNo = userNo;
        this.content = content;
    }
}
