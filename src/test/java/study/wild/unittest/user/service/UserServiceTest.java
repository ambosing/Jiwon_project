package study.wild.unittest.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.common.domain.DuplicateResourceException;
import study.wild.unittest.mock.common.TestPasswordEncoder;
import study.wild.unittest.mock.user.FakeUserRepository;
import study.wild.user.controller.response.port.UserService;
import study.wild.user.domain.User;
import study.wild.user.domain.UserCreate;
import study.wild.user.service.UserServiceImpl;
import study.wild.user.service.port.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.userRepository = new FakeUserRepository();
        this.userService = UserServiceImpl.builder()
                .userRepository(this.userRepository)
                .passwordEncoder(new TestPasswordEncoder())
                .build();
    }

    @Test
    @DisplayName("새로운 유저를 생성할 수 있다")
    void 새로운_유저를_생성할_수_있다() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        //when
        User user = userService.create(userCreate);
        //then
        assertThat(user.getNo()).isNotNull();
        assertThat(user.getId().id()).isEqualTo("ambosing");
        assertThat(user.getName().name()).isEqualTo("jiwon");
        assertThat(user.getPassword().password()).isEqualTo("passwordTest");
    }

    @Test
    @DisplayName("중복된 유저가 존재할 경우 같은 id로 생성할 수 없다")
    void 중복된_유저가_존재할_경우_같은_id로_생성할_수_없다() {
        //given
        User user = User.builder()
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        UserCreate userCreate = UserCreate.builder()
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> userService.create(userCreate))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("User에서 ID ambosing가 이미 존재합니다.");
    }

    @Test
    @DisplayName("유저를 삭제할 수 있다")
    void 유저를_삭제할_수_있다() {
        //given
        User user = User.builder()
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        User savedUser = userRepository.save(user);
        //when
        Long deletedNo = userService.delete(savedUser.getNo());
        //then
        assertThat(deletedNo).isEqualTo(savedUser.getNo());
        User user1 = userRepository.getByNo(savedUser.getNo());
        assertThat(user1.getDeletedDate()).isNotNull();
    }
}
