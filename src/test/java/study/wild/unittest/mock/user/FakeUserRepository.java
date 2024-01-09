package study.wild.unittest.mock.user;

import study.wild.user.domain.User;
import study.wild.user.service.port.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {
    private Long id = 0L;
    private final List<User> data = new ArrayList<>();


    @Override
    public User save(User user) {
        if (user.getNo() == null || user.getNo() == 0) {
            User newUser = User.builder()
                    .no(++id)
                    .id(user.getId().id())
                    .name(user.getName().name())
                    .password(user.getPassword().password())
                    .createdDate(LocalDateTime.now())
                    .deletedDate(null)
                    .build();
            data.add(newUser);
            return newUser;
        } else {
            data.removeIf(item -> Objects.equals(item.getNo(), user.getNo()));
            data.add(user);
            return user;
        }
    }

    @Override
    public Optional<User> findById(String id) {
        return data.stream()
                .filter(item -> Objects.equals(item.getId().id(), id))
                .findAny();
    }
}
