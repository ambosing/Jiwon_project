package study.wild.post.controller;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.wild.post.controller.port.PostService;
import study.wild.post.controller.response.PostListResponse;
import study.wild.post.controller.response.PostResponse;
import study.wild.post.domain.PostCreate;
import study.wild.post.domain.PostUpdate;

@Builder
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(PostResponse.from(postService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<Page<PostListResponse>> getByCategoryId(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "count", required = false) Long totalCount,
            Pageable pageable) {
        return ResponseEntity.ok()
                .body(postService.getByCategoryId(categoryId, totalCount, pageable));
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreate postCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostResponse.from(postService.create(postCreate)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody PostUpdate postUpdate) {
        return ResponseEntity.ok()
                .body(PostResponse.from(postService.update(id, postUpdate)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(postService.delete(id));
    }
}
