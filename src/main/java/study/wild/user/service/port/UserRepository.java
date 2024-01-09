package study.wild.user.service.port;

import study.wild.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(String id);
}
