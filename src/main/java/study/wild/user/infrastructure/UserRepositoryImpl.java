package study.wild.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.user.domain.User;
import study.wild.user.service.port.UserRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toDomain();
    }

    public Optional<User> findById(String id) {
        return userJpaRepository
                .findById(id)
                .map(UserEntity::toDomain);
    }

    @Override
    public User getByNo(Long no) {
        return userJpaRepository
                .findById(no)
                .map(UserEntity::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("User", no));
    }
}
