package study.wild.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
public class User {
    private final Long no;
    private final UserId id;
    private final UserPassword password;
    private final UserName name;
    private final LocalDateTime createdDate;
    private final LocalDateTime deletedDate;

    @Builder
    private User(Long no, String id, String password, String name, LocalDateTime createdDate, LocalDateTime deletedDate) {
        this.no = no;
        this.id = UserId.builder()
                .id(id)
                .build();
        this.password = UserPassword.builder()
                .password(password)
                .build();
        this.name = UserName.builder()
                .name(name)
                .build();
        this.createdDate = createdDate;
        this.deletedDate = deletedDate;
    }

    public static User fromCreate(UserCreate userCreate, PasswordEncoder passwordEncoder) {
        return User.builder()
                .id(userCreate.getId())
                .name(userCreate.getName())
                .password(passwordEncoder.encode(userCreate.getPassword()))
                .build();
    }

    public User delete() {
        return User.builder()
                .no(no)
                .id(id.id())
                .password(password.password())
                .name(name.name())
                .createdDate(createdDate)
                .deletedDate(LocalDateTime.now())
                .build();
    }
}
