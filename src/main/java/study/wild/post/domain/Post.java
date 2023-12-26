package study.wild.post.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import study.wild.category.domain.Category;
import study.wild.comment.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Post {
    private final Long id;
    private final PostTitle title;
    private final PostContent content;
    private final Long view;
    private final List<Comment> comments;
    private final Category category;
    private final LocalDateTime deletedDate;

    @Builder
    public Post(Long id, String title, String content, Long view, List<Comment> comments, Category category, LocalDateTime deletedDate) {
        this.id = id;
        this.title = PostTitle.builder()
                .title(title)
                .build();
        this.content = PostContent.builder()
                .content(content)
                .build();
        this.view = view;
        this.comments = comments;
        this.category = category;
        this.deletedDate = deletedDate;
    }

    public static Post fromCreate(Category category, PostCreate postCreate) {
        return Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .view(0L)
                .category(category)
                .build();
    }

    public Post delete(LocalDateTime deletedDate) {
        return Post.builder()
                .id(id)
                .title(title.title())
                .content(content.content())
                .view(view)
                .deletedDate(deletedDate)
                .category(category)
                .build();
    }
}
