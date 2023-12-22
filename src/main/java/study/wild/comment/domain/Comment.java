package study.wild.comment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.wild.post.domain.Post;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Comment {
    private Long id;
    private Post post;
    private String content;
    private LocalDateTime deletedDate;

    @Builder
    public Comment(Long id, Post post, String content, LocalDateTime deletedDate) {
        this.id = id;
        this.post = post;
        this.content = content;
        this.deletedDate = deletedDate;
    }
}
