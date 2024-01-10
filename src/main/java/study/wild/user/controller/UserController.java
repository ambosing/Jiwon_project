package study.wild.user.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.wild.user.controller.response.port.UserService;
import study.wild.user.controller.response.response.UserResponse;
import study.wild.user.domain.UserCreate;

@Builder
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserCreate userCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.from(userService.create(userCreate)));
    }
}
