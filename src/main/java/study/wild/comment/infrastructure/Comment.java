package study.wild.comment.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import study.wild.common.infrastructure.BaseTimeEntity;
import study.wild.post.infrastructure.PostEntity;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(nullable = false, length = 128)
    private String content;

    private LocalDateTime deletedDate;
}
