package study.wild.user.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.wild.common.domain.DuplicateResourceException;
import study.wild.user.controller.response.port.UserService;
import study.wild.user.domain.User;
import study.wild.user.domain.UserCreate;
import study.wild.user.service.port.UserRepository;

import java.util.Optional;

@Builder
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(UserCreate userCreate) {
        Optional<User> user = userRepository.findById(userCreate.getId());
        if (user.isPresent()) {
            throw new DuplicateResourceException("User", user.get().getId().id());
        }
        return userRepository.save(User.fromCreate(userCreate, passwordEncoder));
    }
}