package study.wild.post.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PostListResponse {
    private Long id;
    private String title;
    private String content;
    private Long view;

    @QueryProjection
    public PostListResponse(Long id, String title, String content, Long view) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
    }
}
