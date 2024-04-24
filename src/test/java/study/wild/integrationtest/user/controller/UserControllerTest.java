package study.wild.integrationtest.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import study.wild.user.controller.UserController;
import study.wild.user.domain.UserCreate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/user/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("비회원인 사용자는 회원 가입을 할 수 있다")
    void 비회원인_사용자는_회원_가입을_할_수_있다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .id("ambosing")
                .name("jiwon")
                .password("password")
                .build();
        //when
        //then
        mockMvc.perform(post("/signup").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.no").isNumber())
                .andExpect(jsonPath("$.id").value("ambosing"))
                .andExpect(jsonPath("$.name").value("jiwon"));
    }

    @Test
    @DisplayName("사용자는 중복된 아이디로 회원가입할 수 있다")
    void 사용자는_중복된_아이디로_회원가입할_수_있다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .id("ambosing1")
                .name("jiwon")
                .password("password")
                .build();
        //when
        //then
        mockMvc.perform(post("/signup").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreate)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User에서 ID ambosing1가 이미 존재합니다."));
    }

    @Test
    @DisplayName("사용자는 계정을 삭제할 수 있다")
    void 사용자는_계정을_삭제할_수_있다() throws Exception {
        //given
        Long deleteNo = 1L;
        //when
        //then
        mockMvc.perform(delete("/users/{no}", deleteNo).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(deleteNo.toString()));
    }

    @Test
    @DisplayName("사용자는 없는 계정을 삭제할 수 없다")
    void 사용자는_없는_계정을_삭제할_수_없다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/users/{no}", 9999999L).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User에서 ID 9999999를 찾을 수 없습니다."));
    }
}
