package study.wild.comment.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.wild.comment.controller.port.CommentService;
import study.wild.comment.controller.response.CommentResponse;
import study.wild.comment.domain.CommentCreate;

@Builder
@RestController
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(@Valid @RequestBody CommentCreate commentCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommentResponse.from(commentService.create(commentCreate)));
    }
}
