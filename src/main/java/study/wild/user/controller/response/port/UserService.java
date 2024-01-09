package study.wild.user.controller.response.port;

import study.wild.user.domain.User;
import study.wild.user.domain.UserCreate;

public interface UserService {
    User create(UserCreate userCreate);
}
