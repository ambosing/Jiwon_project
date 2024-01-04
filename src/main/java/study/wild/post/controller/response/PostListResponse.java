package study.wild.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import study.wild.post.infrastructure.PostListQuery;

import java.util.List;

@Getter
public class PostListResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Long view;

    @Builder
    private PostListResponse(PostListQuery postListQuery) {
        this.id = postListQuery.getId();
        this.title = postListQuery.getTitle();
        this.content = postListQuery.getContent();
        this.view = postListQuery.getView();
    }

    public static Page<PostListResponse> from(Page<PostListQuery> posts) {
        List<PostListResponse> postListResponses = posts.getContent().stream()
                .map(PostListResponse::new)
                .toList();

        return new PageImpl<>(postListResponses, posts.getPageable(), posts.getTotalElements());
    }
}
