package study.wild.post.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.wild.post.controller.port.PostService;
import study.wild.post.controller.response.PostResponse;

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
}
