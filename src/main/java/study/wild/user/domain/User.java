package study.wild.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.wild.user.infrastructure.UserCreate;

import java.time.LocalDateTime;

@Getter
public class User {
    private final Long no;
    private final String id;
    private final String password;
    private final String name;
    private final LocalDateTime createdDate;
    private final LocalDateTime deletedDate;

    @Builder
    private User(Long no, String id, String password, String name, LocalDateTime createdDate, LocalDateTime deletedDate) {
        this.no = no;
        this.id = id;
        this.password = password;
        this.name = name;
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
}
