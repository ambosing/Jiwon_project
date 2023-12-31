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
import study.wild.comment.domain.CommentUpdate;
import study.wild.common.domain.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("Comment를 수정할 수 있다")
    void Comment를_수정할_수_있다() {
        //given
        CommentUpdate commentUpdate = CommentUpdate.builder()
                .content("updateTest")
                .postId(1L)
                .build();
        //when
        Comment updatedComment = commentService.update(1L, commentUpdate);
        //then
        assertThat(updatedComment.getContent().content()).isEqualTo("updateTest");
        assertThat(updatedComment.getPost().getContent().content()).isEqualTo("content1");
        assertThat(updatedComment.getPost().getTitle().title()).isEqualTo("title1");
    }

    @Test
    @DisplayName("Comment를 삭제할 수 있다")
    void Comment를_삭제할_수_있다() {
        //given
        Long id = 1L;
        //when
        Long deletedId = commentService.delete(id);
        //then
        assertThat(deletedId).isEqualTo(id);
    }

    @Test
    @DisplayName("없는 Id를 삭제할 수 없다")
    void 없는_Id를_삭제할_수_없다() {
        //given
        Long notFoundId = 9999999L;
        //when
        //then
        assertThatThrownBy(() -> commentService.delete(notFoundId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Comment에서 ID %d를 찾을 수 없습니다.", notFoundId);
    }
}
