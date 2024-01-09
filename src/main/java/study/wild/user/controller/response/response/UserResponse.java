package study.wild.user.controller.response.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.wild.user.domain.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponse {
    private Long no;
    private String id;
    private String name;
    private LocalDateTime createdDate;

    @Builder
    private UserResponse(Long no, String id, String name, LocalDateTime createdDate) {
        this.no = no;
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .no(user.getNo())
                .id(user.getId().id())
                .name(user.getName().name())
                .createdDate(user.getCreatedDate())
                .build();
    }
    
}
