package study.wild.post.infrastructure;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import study.wild.category.controller.response.CategoryResponse;
import study.wild.comment.domain.Comment;
import study.wild.user.controller.response.response.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostQuery {
    private final Long id;
    private final String title;
    private final String content;
    private final Long view;
    private final LocalDateTime createdDate;
    private List<Comment> comments;
    private final CategoryResponse category;
    private final UserResponse user;

    @Builder
    @QueryProjection
    public PostQuery(Long id, String title, String content, Long view, LocalDateTime createdDate, Long categoryId, String categoryName, Long userNo, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.createdDate = createdDate;
        this.user = UserResponse.builder()
                .no(userNo)
                .name(userName)
                .build();
        this.category = CategoryResponse.builder()
                .id(categoryId)
                .name(categoryName)
                .build();
    }

}