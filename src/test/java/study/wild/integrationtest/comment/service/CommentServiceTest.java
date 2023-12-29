package study.wild.integrationtest.comment.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import study.wild.comment.controller.port.CommentService;
import study.wild.comment.domain.Comment;
import study.wild.comment.domain.CommentCreate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/comment/comment-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class CommentServiceTest {

    @Autowired
    private CommentService commentService;


    @Test
    @DisplayName("새로운 Comment 생성할 수 있다")
    void 새로운_Comment_생성할_수_있다() {
        //given
        Long postId = 1L;
        CommentCreate commentCreate = CommentCreate.builder()
                .postId(postId)
                .content("CreateTest")
                .build();
        //when
        Comment comment = commentService.create(commentCreate);
        //then
        assertThat(comment.getId()).isNotNull();
        assertThat(comment.getContent().content()).isEqualTo("CreateTest");
        assertThat(comment.getPost().getId()).isEqualTo(postId);
    }
}
