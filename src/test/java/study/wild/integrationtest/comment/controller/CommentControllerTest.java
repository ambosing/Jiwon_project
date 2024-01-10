package study.wild.integrationtest.comment.controller;

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
import study.wild.comment.domain.CommentCreate;
import study.wild.comment.domain.CommentUpdate;
import study.wild.comment.infrastructure.CommentEntity;
import study.wild.comment.infrastructure.CommentJpaRepository;
import study.wild.common.domain.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/comment/comment-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentJpaRepository commentJpaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("사용자는 새로운 댓글을 생성할 수 있다")
    void 사용자는_새로운_댓글을_생성할_수_있다() throws Exception {
        //given
        CommentCreate commentCreate = CommentCreate.builder()
                .content("createTest")
                .postId(1L)
                .build();
        //when
        //then
        mockMvc.perform(post("/comment").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.content").value("createTest"));
    }

    @Test
    @DisplayName("사용자는 새로운 댓글을 생성할 때 내용을 입력하지 않으면 생성할 수 없다")
    void 사용자는_새로운_댓글을_생성할_때_내용을_입력하지_않으면_생성할_수_없다() throws Exception {
        //given
        CommentCreate commentCreate = CommentCreate.builder()
                .content("")
                .postId(1L)
                .build();
        //when
        //then
        mockMvc.perform(post("/comment").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentCreate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자는 댓글을 수정할 수 있다")
    void 사용자는_댓글을_수정할_수_있다() throws Exception {
        //given
        CommentUpdate commentUpdate = CommentUpdate.builder()
                .content("update")
                .postId(1L)
                .build();
        //when
        //then
        mockMvc.perform(patch("/comment/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.content").value("update"));
    }

    @Test
    @DisplayName("사용자는 빈칸으로 댓글 내용을 수정할 수 없다")
    void 사용자는_빈칸으로_댓글_내용을_수정할_수_없다() throws Exception {
        //given
        CommentUpdate commentUpdate = CommentUpdate.builder()
                .content("")
                .postId(1L)
                .build();
        //when
        //then
        mockMvc.perform(patch("/comment/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentUpdate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("사용자는 댓글을 삭제할 수 있다")
    void 사용자는_댓글을_삭제할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/comment/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        CommentEntity comment = commentJpaRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", 1L));
        assertThat(comment.getDeletedDate()).isNotNull();
    }

    @Test
    @DisplayName("사용자는 없는 댓글을 삭제할 수 없다")
    void 사용자는_없는_댓글을_삭제할_수_없다() throws Exception {
        //given
        Long notFoundId = 9999999L;
        //when
        //then
        mockMvc.perform(delete("/comment/{id}", notFoundId).with(csrf()))
                .andExpect(status().isNotFound());
    }
}
