package study.wild.integrationtest.category.controller;

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
import study.wild.category.domain.CategoryCreate;
import study.wild.category.domain.CategoryUpdate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/category/category-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("사용자는 모든 카테고리를 조회할 수 있다")
    void 사용자는_모든_카테고리를_조회할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[0].name").value("category1"))
                .andExpect(jsonPath("$[1].name").value("category2"));
    }

    @Test
    @DisplayName("사용자는 카테고리를 단건 조회할 수 있다")
    void 사용자는_카테고리를_단건_조회할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("category1"));
    }

    @Test
    @DisplayName("사용자는 없는 카테고리를 조회할 경우 에러가 발생한다")
    void 사용자는_없는_카테고리를_조회할_경우_에러가_발생한다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/categories/123456789"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Category에서 ID 123456789를 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("사용자는 삭제한 카테고리를 조회할 경우 에러에 마주한다")
    void 사용자는_삭제한_카테고리를_조회할_경우_에러에_마주한다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/categories/3"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Category에서 ID 3를 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("사용자는 카테고리를 생성할 수 있다")
    void 사용자는_카테고리를_생성할_수_있다() throws Exception {
        //given
        CategoryCreate createTest = CategoryCreate.builder()
                .name("CreateTest")
                .build();
        //when
        //then
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("CreateTest"));
    }

    @Test
    @DisplayName("사용자는 카테고리를 수정할 수 있다")
    void 사용자는_카테고리를_수정할_수_있다() throws Exception {
        //given
        CategoryUpdate updateTest = CategoryUpdate.builder()
                .name("UpdateTest")
                .deleteDateTime(null)
                .build();
        //when
        //then
        mockMvc.perform(patch("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("UpdateTest"));
    }

    @Test
    @DisplayName("사용자는 카테고리 이름을 빈칸으로 NULL로 변경할 수 없다")
    void 사용자는_카테고리_이름을_빈칸으로_변경할_수_없다() throws Exception {
        //given
        CategoryUpdate updateTest = CategoryUpdate.builder()
                .name("")
                .build();
        //when
        //then
        mockMvc.perform(patch("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("이름을 입력해주세요."));
    }

    @Test
    @DisplayName("사용자는 카테고리 이름을 NULL로 변경할 수 없다")
    void 사용자는_카테고리_이름을_NULL로_변경할_수_없다() throws Exception {
        //given
        CategoryUpdate updateTest = CategoryUpdate.builder()
                .name(null)
                .build();
        //when
        //then
        mockMvc.perform(patch("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("이름을 입력해주세요."));
    }

    @Test
    @DisplayName("사용자는_카테고리를 삭제할 수 있다")
    void 사용자는_카테고리를_삭제할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
