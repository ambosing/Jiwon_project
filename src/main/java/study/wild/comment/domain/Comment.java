package study.wild.comment.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import study.wild.post.domain.Post;
import study.wild.user.domain.User;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class Comment {
    private final Long id;
    private final Post post;
    private final User user;
    private final CommentContent content;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;
    private final LocalDateTime deletedDate;

    @Builder
    private Comment(Long id, Post post, User user, String content, LocalDateTime createdDate, LocalDateTime lastModifiedDate, LocalDateTime deletedDate) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.content = CommentContent.builder()
                .content(content)
                .build();
        this.deletedDate = deletedDate;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static Comment fromCreate(Post post, User user, CommentCreate commentCreate) {
        return Comment.builder()
                .content(commentCreate.getContent())
                .post(post)
                .user(user)
                .build();
    }

    public Comment update(CommentUpdate commentUpdate) {
        return Comment.builder()
                .id(id)
                .content(commentUpdate.content())
                .post(post)
                .user(user)
                .build();
    }

    public Comment delete(LocalDateTime deletedDate) {
        return Comment.builder()
                .id(id)
                .content(content.content())
                .post(post)
                .user(user)
                .deletedDate(deletedDate)
                .build();
    }
}
