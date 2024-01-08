package study.wild.unittest.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.wild.unittest.mock.common.TestPasswordEncoder;
import study.wild.user.domain.User;
import study.wild.user.infrastructure.UserCreate;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(user.getId()).isEqualTo("ambosing");
        assertThat(user.getName()).isEqualTo("jiwon");
        assertThat(user.getPassword()).isEqualTo("passwordTest");
    }
}
