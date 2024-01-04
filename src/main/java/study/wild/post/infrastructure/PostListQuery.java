package study.wild.post.infrastructure;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PostListQuery {
    private final Long id;
    private final String title;
    private final String content;
    private final Long view;

    @QueryProjection
    public PostListQuery(Long id, String title, String content, Long view) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
    }
}
