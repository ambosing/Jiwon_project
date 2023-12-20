package study.wild.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import study.wild.category.domain.Category;
import study.wild.comment.infrastructure.Comment;
import study.wild.post.domain.Post;

import java.util.List;

@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Long view;
    private final List<Comment> comments;
    private final Category category;

    @Builder
    public PostResponse(Long id, String title, String content, Long view, List<Comment> comments, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.comments = comments;
        this.category = category;
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .content(post.getContent())
                .view(post.getView())
                .title(post.getTitle())
                .category(post.getCategory())
                .comments(post.getComments())
                .build();
    }
}
