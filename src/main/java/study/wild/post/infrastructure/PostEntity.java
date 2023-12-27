package study.wild.post.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.wild.category.infrastructure.CategoryEntity;
import study.wild.comment.domain.Comment;
import study.wild.comment.infrastructure.CommentEntity;
import study.wild.common.infrastructure.BaseTimeEntity;
import study.wild.post.domain.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 64)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(columnDefinition = "BIGINT default 0")
    private Long view;

    @OneToMany(mappedBy = "post")
    private List<CommentEntity> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column
    private LocalDateTime deletedDate;


    @Builder
    public PostEntity(Long id, String title, String content, Long view, List<CommentEntity> comments, CategoryEntity category, LocalDateTime deletedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.comments = comments;
        this.category = category;
        this.deletedDate = deletedDate;
    }

    public static PostEntity from(Post post) {

        List<CommentEntity> commentsEntity = null;
        if (post.getComments() != null) {
            commentsEntity = post.getComments().stream()
                    .map(CommentEntity::from)
                    .toList();
        }
        return PostEntity.builder()
                .id(post.getId())
                .title(post.getTitle().title())
                .category(CategoryEntity.from(post.getCategory()))
                .comments(commentsEntity)
                .deletedDate(post.getDeletedDate())
                .content(post.getContent().content())
                .view(post.getView())
                .build();
    }

    public Post toDomain() {
        List<Comment> comments = null;
        if (this.comments != null) {
            comments = this.comments.stream()
                    .map(CommentEntity::toDomain)
                    .toList();
        }

        return Post.builder()
                .id(id)
                .content(content)
                .category(category.toDomain())
                .comments(comments)
                .deletedDate(deletedDate)
                .view(view)
                .title(title)
                .build();
    }
}
