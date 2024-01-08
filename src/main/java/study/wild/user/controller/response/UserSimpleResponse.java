package study.wild.user.controller.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.wild.user.domain.User;

@Getter
@NoArgsConstructor
public class UserSimpleResponse {
    private Long no;
    private String id;

    @Builder
    public UserSimpleResponse(Long no, String id) {
        this.no = no;
        this.id = id;
    }

    public static UserSimpleResponse from(User user) {
        return UserSimpleResponse.builder()
                .no(user.getNo())
                .id(user.getId())
                .build();
    }
}
