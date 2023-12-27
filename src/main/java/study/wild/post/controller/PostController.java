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

@Builder
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(PostResponse.from(postService.getById(id)));
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<Page<PostListResponse>> getByCategoryId(@PathVariable("categoryId") Long categoryId,
                                                                  @RequestParam("count") Long totalCount,
                                                                  Pageable pageable) {
        return ResponseEntity.ok()
                .body(postService.getByCategoryId(categoryId, totalCount, pageable));
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreate postCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostResponse.from(postService.create(postCreate)));
    }


}
