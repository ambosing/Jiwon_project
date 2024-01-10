package study.wild.post.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.wild.category.infrastructure.CategoryEntity;
import study.wild.common.infrastructure.BaseTimeEntity;
import study.wild.post.domain.Post;
import study.wild.user.infrastructure.UserEntity;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserEntity user;

    @Column
    private LocalDateTime deletedDate;


    @Builder
    private PostEntity(Long id, String title, String content, Long view, CategoryEntity category, UserEntity user, LocalDateTime deletedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.category = category;
        this.user = user;
        this.deletedDate = deletedDate;
    }

    public static PostEntity from(Post post) {
        return PostEntity.builder()
                .id(post.getId())
                .title(post.getTitle().title())
                .category(CategoryEntity.from(post.getCategory()))
                .deletedDate(post.getDeletedDate())
                .content(post.getContent().content())
                .view(post.getView())
                .build();
    }

    public Post toDomain() {
        return Post.builder()
                .id(id)
                .content(content)
                .category(category.toDomain())
                .deletedDate(deletedDate)
                .view(view)
                .title(title)
                .build();
    }
}
