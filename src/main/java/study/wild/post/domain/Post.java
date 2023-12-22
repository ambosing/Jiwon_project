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
    private Long id;
    private PostTitle title;
    private PostContent content;
    private Long view;
    private List<Comment> comments;
    private Category category;
    private LocalDateTime deletedDate;

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

    public void delete(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
