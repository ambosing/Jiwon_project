package study.wild.category.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.wild.category.controller.port.CategoryService;
import study.wild.category.controller.response.CategoryResponse;
import study.wild.category.service.dto.CategoryCreate;
import study.wild.category.service.dto.CategoryUpdate;

import java.util.List;

@Builder
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok().body(
                categoryService
                        .findAll()
                        .stream()
                        .map(CategoryResponse::from)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(
                CategoryResponse.from(categoryService.getById(id))
        );
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryCreate categoryCreate) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoryResponse.from(categoryService.create(categoryCreate)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long id,
                                                   @RequestBody CategoryUpdate categoryUpdate) {
        return ResponseEntity.ok()
                .body(CategoryResponse.from(categoryService.update(id, categoryUpdate)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body(categoryService.delete(id));
    }
}