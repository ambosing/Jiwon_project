package study.wild.integrationtest.post.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import study.wild.post.domain.PostCreate;
import study.wild.post.domain.PostUpdate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/post/post-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("사용자는 단건의 게시물을 조회할 수 있다")
    void 사용자는_단건의_게시물을_조회할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.content").value("content1"));
    }

    @Test
    @DisplayName("사용자는 단건의 게시물을 댓글과 카테고리를 함께 조회할 수 있다")
    void 사용자는_단건의_게시물을_댓글과_카테고리를_함께_조회할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/posts/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("title1"))
                .andExpect(jsonPath("$.content").value("content1"))
                .andExpect(jsonPath("$.category.name").value("category1"))
                .andExpectAll(
                        jsonPath("$.comments.commentListResponse[0].content").value("comment1"),
                        jsonPath("$.comments.commentListResponse[1].content").value("comment2")
                );
    }

    @Test
    @DisplayName("사용자는 카테고리를 고르지 않으면 모든 게시물들을 확인할 수 있다")
    void 사용자는_카테고리를_고르지_않으면_모든_게시물들을_확인할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(3))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("title3"))
                .andExpect(jsonPath("$.content[1].title").value("title2"))
                .andExpect(jsonPath("$.content[2].title").value("title1"))
                .andExpect(jsonPath("$.content[0].content").value("content3"))
                .andExpect(jsonPath("$.content[1].content").value("content2"))
                .andExpect(jsonPath("$.content[2].content").value("content1"));
    }

    @Test
    @DisplayName("사용자는 특정 카테고리의 게시물들을 확인할 수 있다")
    void 사용자는_특정_카테고리의_게시물들을_확인할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/posts")
                        .queryParam("categoryId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(3))
                .andExpect(jsonPath("$.content[0].title").value("title3"))
                .andExpect(jsonPath("$.content[0].content").value("content3"));
    }

    @Test
    @DisplayName("사용자는 게시물들을 페이징해서 볼 수 있다")
    void 사용자는_게시물들을_페이징해서_볼_수_있다() {
        //given
        //when
        //then
        Assertions.assertAll(
                () -> mockMvc.perform(get("/posts")
                                .queryParam("categoryId", "1")
                                .queryParam("page", "0")
                                .queryParam("size", "1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].id").value(2))
                        .andExpect(jsonPath("$.content[0].title").value("title2"))
                        .andExpect(jsonPath("$.content[0].content").value("content2"))
                        .andExpect(jsonPath("$.totalElements").value("2")),
                () -> mockMvc.perform(get("/posts")
                                .queryParam("categoryId", "1")
                                .queryParam("count", "2")
                                .queryParam("page", "1")
                                .queryParam("size", "1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content[0].id").value(1))
                        .andExpect(jsonPath("$.content[0].title").value("title1"))
                        .andExpect(jsonPath("$.content[0].content").value("content1"))
                        .andExpect(jsonPath("$.totalElements").value("2"))
        );
    }

    @Test
    @DisplayName("사용자는 새로운 게시물을 생성할 수 있다")
    void 사용자는_새로운_게시물을_생성할_수_있다() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("newTitle")
                .content("newContent")
                .categoryId(1L)
                .build();
        //when
        //then
        mockMvc.perform(post("/posts").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("newTitle"))
                .andExpect(jsonPath("$.content").value("newContent"))
                .andExpect(jsonPath("$.view").value(0L));
    }

    @Test
    @DisplayName("사용자는 게시물을 수정할 수 있다")
    void 사용자는_게시물을_수정할_수_있다() throws Exception {
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .title("updateTitle")
                .content("updateContent")
                .categoryId(1L)
                .build();
        //when
        //then
        mockMvc.perform(patch("/posts/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("updateTitle"))
                .andExpect(jsonPath("$.content").value("updateContent"));
    }

    @Test
    @DisplayName("사용자는 게시물을 삭제할 수 있다")
    void 사용자는_게시물을_삭제할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/posts/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
