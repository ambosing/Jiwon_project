package study.wild.comment.infrastructure;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import study.wild.user.controller.response.response.UserResponse;

import java.time.LocalDateTime;

@Getter
public class CommentQuery {
    private final Long id;
    private final String content;
    private final UserResponse user;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    @Builder
    @QueryProjection
    public CommentQuery(Long id, String content, Long userNo, String userId, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.content = content;
        this.user = UserResponse.builder()
                .no(userNo)
                .id(userId)
                .build();
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
