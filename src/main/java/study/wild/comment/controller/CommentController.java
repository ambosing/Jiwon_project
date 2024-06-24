package study.wild.comment.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.wild.comment.controller.port.CommentService;
import study.wild.comment.controller.response.CommentResponse;
import study.wild.comment.domain.CommentCreate;
import study.wild.comment.domain.CommentUpdate;

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

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CommentUpdate commentUpdate) {
        return ResponseEntity.ok()
                .body(CommentResponse.from(commentService.update(id, commentUpdate)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(commentService.delete(id));
    }
}
