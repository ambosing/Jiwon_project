package study.wild.unittest.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import study.wild.unittest.mock.common.TestPasswordEncoder;
import study.wild.user.domain.User;
import study.wild.user.domain.UserCreate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {
    @Test
    @DisplayName("UserCreate를 User로 변경할 수 있다")
    void UserCreate를_User로_변경할_수_있다() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .name("jiwon")
                .id("ambosing")
                .password("password")
                .build();
        //when
        User user = User.fromCreate(userCreate, new TestPasswordEncoder());
        //then
        assertThat(user.getId().id()).isEqualTo("ambosing");
        assertThat(user.getName().name()).isEqualTo("jiwon");
        assertThat(user.getPassword().password()).isEqualTo("passwordTest");
    }

    @DisplayName("유저 이름이 null이거나 공백일 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void 유저_이름이_null이거나_공백일_수_없다(String name) {
        //given
        UserCreate userCreate = UserCreate.builder()
                .id("ambosing")
                .name(name)
                .password("password")
                .build();
        //when
        //then
        assertThatThrownBy(() -> User.fromCreate(userCreate, new TestPasswordEncoder()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름을 입력해주세요.");
    }

    @DisplayName("유저 id가 null이거나 공백일 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void 유저_id가_null이거나_공백일_수_없다(String id) {
        //given
        UserCreate userCreate = UserCreate.builder()
                .id(id)
                .name("jiwon")
                .password("password")
                .build();
        //when
        //then
        assertThatThrownBy(() -> User.fromCreate(userCreate, new TestPasswordEncoder()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id를 입력해주세요.");
    }

    @Test
    @DisplayName("유저 id는 4글자를 넘어야 합니다")
    void 유저_id는_4글자를_넘어야_합니다() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .id("id")
                .name("jiwon")
                .password("password")
                .build();
        //when
        //then
        assertThatThrownBy(() -> User.fromCreate(userCreate, new TestPasswordEncoder()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id는 4자 이상 입력해주세요.");
    }
}
