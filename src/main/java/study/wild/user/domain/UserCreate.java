package study.wild.user.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreate {
    @NotEmpty
    @Min(4)
    private String id;
    @NotEmpty
    @Min(8)
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
