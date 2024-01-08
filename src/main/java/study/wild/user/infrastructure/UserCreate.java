package study.wild.user.infrastructure;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreate {
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;

    @Builder
    private UserCreate(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
}
