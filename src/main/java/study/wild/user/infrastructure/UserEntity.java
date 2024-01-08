package study.wild.user.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.wild.common.infrastructure.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseTimeEntity {
    @Id
    @Column(name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "user_id", nullable = false)
    private String id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column
    private LocalDateTime deletedDate;

    @Builder
    private UserEntity(Long no, String id, String password, String name, LocalDateTime deletedDate) {
        this.no = no;
        this.id = id;
        this.password = password;
        this.name = name;
        this.createdDate = getCreatedDate();
        this.lastModifiedDate = getLastModifiedDate();
        this.deletedDate = deletedDate;
    }
}
