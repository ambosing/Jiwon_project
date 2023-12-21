package study.wild.post.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.wild.post.controller.port.PostService;
import study.wild.post.controller.response.PostResponse;
import study.wild.post.domain.PostCreate;

@Builder
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(PostResponse.from(postService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreate postCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostResponse.from(postService.create(postCreate)));
    }
}
