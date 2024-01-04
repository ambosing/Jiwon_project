package study.wild.comment.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.wild.comment.domain.Comment;
import study.wild.common.infrastructure.BaseTimeEntity;
import study.wild.post.infrastructure.PostEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(nullable = false, length = 128)
    private String content;

    @Column
    private LocalDateTime deletedDate;

    @Builder
    private CommentEntity(Long id, PostEntity post, String content, LocalDateTime deletedDate) {
        this.id = id;
        this.post = post;
        this.content = content;
        this.deletedDate = deletedDate;
    }

    public static CommentEntity from(Comment comment) {
        return CommentEntity.builder()
                .id(comment.getId())
                .post(PostEntity.from(comment.getPost()))
                .content(comment.getContent().content())
                .deletedDate(comment.getDeletedDate())
                .build();
    }

    public Comment toDomain() {
        return Comment.builder()
                .id(id)
                .content(content)
                .post(post.toDomain())
                .deletedDate(deletedDate)
                .createdDate(getCreatedDate())
                .lastModifiedDate(getLastModifiedDate())
                .build();
    }
}
