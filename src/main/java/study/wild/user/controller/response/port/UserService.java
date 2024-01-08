package study.wild.user.controller.response.port;

import study.wild.user.domain.User;
import study.wild.user.infrastructure.UserCreate;

public interface UserService {
    User create(UserCreate userCreate);
}
