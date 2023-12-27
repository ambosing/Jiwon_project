package study.wild.unittest.mock.post;

import org.springframework.data.domain.Pageable;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.controller.response.PostListResponse;
import study.wild.post.domain.Post;
import study.wild.post.service.port.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FakePostRepository implements PostRepository {
    private Long id = 0L;
    private final List<Post> data = new ArrayList<>();


    @Override
    public Post getById(Long id) {
        return data.stream().
                filter(item -> item.getId().equals(id) && item.getDeletedDate() == null).findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == null || post.getId() == 0) {
            Post newPost = Post.builder()
                    .id(++id)
                    .view(post.getView())
                    .title(post.getTitle().title())
                    .content(post.getContent().content())
                    .deletedDate(post.getDeletedDate())
                    .category(post.getCategory())
                    .comments(post.getComments())
                    .build();
            data.add(newPost);
            return newPost;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), post.getId()));
            data.add(post);
            return post;
        }
    }

    @Override
    public Long countByCategoryId(Long categoryId) {
        if (categoryId == null) {
            return (long) data.size();
        }

        return data.stream()
                .filter(item -> Objects.equals(item.getCategory().getId(), categoryId))
                .count();
    }

    @Override
    public List<PostListResponse> getByCategoryId(Long categoryId, Pageable pageable) {

        System.out.println("-------------------------------------------------------------------");
        System.out.println("pageNumber : " + pageable.getOffset() + " / offsetSize : " + pageable.getPageSize());
        if (categoryId == null) {
            return data.stream()
                    .map(item -> new PostListResponse(item.getId(), item.getTitle().title(), item.getContent().content(), item.getView()))
                    .skip(pageable.getOffset() * pageable.getPageSize())
                    .limit(pageable.getPageSize())
                    .collect(Collectors.toList());
        }
        return data.stream()
                .filter(item -> Objects.equals(item.getCategory().getId(), categoryId))
                .map(item -> new PostListResponse(item.getId(), item.getTitle().title(), item.getContent().content(), item.getView()))
                .skip((long) pageable.getPageSize() * pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }
}
