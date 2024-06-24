package study.wild.unittest.mock.user;

import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.wild.user.controller.UserController;
import study.wild.user.controller.response.port.UserService;
import study.wild.user.service.UserServiceImpl;
import study.wild.user.service.port.UserRepository;

public class TestUserContainer {
    public final UserRepository userRepository;
    public final UserService userService;
    public final UserController userController;

    @Builder
    public TestUserContainer(PasswordEncoder passwordEncoder) {
        this.userRepository = new FakeUserRepository();
        this.userService = UserServiceImpl.builder()
                .passwordEncoder(passwordEncoder)
                .userRepository(this.userRepository)
                .build();
        this.userController = UserController.builder()
                .userService(this.userService)
                .build();
    }
}
