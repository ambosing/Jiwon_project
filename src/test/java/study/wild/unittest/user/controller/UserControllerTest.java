package study.wild.unittest.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study.wild.common.domain.DuplicateResourceException;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.unittest.mock.common.TestPasswordEncoder;
import study.wild.unittest.mock.user.TestUserContainer;
import study.wild.user.controller.response.response.UserResponse;
import study.wild.user.domain.User;
import study.wild.user.domain.UserCreate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserControllerTest {

    @Test
    @DisplayName("사용자는 유저를 생성할 수 있다")
    void 사용자는_유저를_생성할_수_있다() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        TestUserContainer container = TestUserContainer.builder()
                .passwordEncoder(new TestPasswordEncoder())
                .build();
        //when
        ResponseEntity<UserResponse> result = container.userController.signup(userCreate);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getNo()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo("ambosing");
        assertThat(result.getBody().getName()).isEqualTo("jiwon");
    }

    @Test
    @DisplayName("사용자는 중복된 id로 유저를 생성할 수 없다")
    void 사용자는_중복된_id로_유저를_생성할_수_없다() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        User user = User.builder()
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        TestUserContainer container = TestUserContainer.builder()
                .passwordEncoder(new TestPasswordEncoder())
                .build();
        container.userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> container.userController.signup(userCreate))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("User에서 ID ambosing가 이미 존재합니다.");
    }

    @Test
    @DisplayName("사용자는 계정을 삭제할 수 있다")
    void 사용자는_계정을_삭제할_수_있다() {
        //given
        User user = User.builder()
                .no(1L)
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        TestUserContainer container = TestUserContainer.builder()
                .passwordEncoder(new TestPasswordEncoder())
                .build();
        container.userRepository.save(user);
        //when
        ResponseEntity<Long> result = container.userController.delete(1L);
        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isEqualTo(1L);
    }

    @Test
    @DisplayName("사용자는 없는 유저를 삭제할 수 없다")
    void 사용자는_없는_유저를_삭제할_수_없다() {
        //given
        TestUserContainer container = TestUserContainer.builder()
                .passwordEncoder(new TestPasswordEncoder())
                .build();
        //when
        //then
        assertThatThrownBy(() -> container.userController.delete(99999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User에서 ID 99999를 찾을 수 없습니다.");
    }
}
