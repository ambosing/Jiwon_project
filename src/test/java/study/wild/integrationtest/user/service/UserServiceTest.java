package study.wild.integrationtest.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import study.wild.common.domain.DuplicateResourceException;
import study.wild.user.controller.response.port.UserService;
import study.wild.user.domain.User;
import study.wild.user.domain.UserCreate;
import study.wild.user.service.port.UserRepository;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
	@Sql(value = "/sql/user/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		Mockito.when(passwordEncoder.encode(anyString()))
			.thenAnswer((Answer<String>)invocation -> invocation.getArgument(0).toString() + "Test");
	}

	@Test
	@DisplayName("새로운 사용자를 생성할 수 있다")
	void 새로운_사용자를_생성할_수_있다() {
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
	@DisplayName("중복된 id로 사용자를 생성할 수 없다")
	void 중복된_id로_사용자를_생성할_수_없다() {
		//given
		UserCreate userCreate = UserCreate.builder()
			.id("ambosing1")
			.name("jiwon")
			.password("password")
			.build();
		//when
		//then
		assertThatThrownBy(() -> userService.create(userCreate))
			.isInstanceOf(DuplicateResourceException.class)
			.hasMessage("User에서 ID ambosing1가 이미 존재합니다.");
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
