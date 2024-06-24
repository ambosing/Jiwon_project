package study.wild.comment.controller.response;

import lombok.Builder;
import lombok.Getter;
import study.wild.comment.domain.Comment;
import study.wild.comment.infrastructure.CommentQuery;
import study.wild.user.controller.response.response.UserResponse;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long id;
    private final String content;
    private final UserResponse user;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    @Builder
    private CommentResponse(Long id, String content, UserResponse user, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static CommentResponse from(CommentQuery comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .user(comment.getUser())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .lastModifiedDate(comment.getLastModifiedDate())
                .build();
    }

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .user(UserResponse.from(comment.getUser()))
                .content(comment.getContent().content())
                .createdDate(comment.getCreatedDate())
                .lastModifiedDate(comment.getLastModifiedDate())
                .build();
    }


}
