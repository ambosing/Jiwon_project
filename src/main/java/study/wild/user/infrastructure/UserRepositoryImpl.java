package study.wild.user.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.user.domain.User;
import study.wild.user.service.port.UserRepository;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
