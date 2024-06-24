package study.wild.unittest.mock.post;

import org.springframework.data.domain.Pageable;
import study.wild.common.domain.ResourceNotFoundException;
import study.wild.post.domain.Post;
import study.wild.post.infrastructure.PostListQuery;
import study.wild.post.infrastructure.PostQuery;
import study.wild.post.service.port.PostRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FakePostRepository implements PostRepository {
    private Long id = 0L;
    private final List<Post> data = new ArrayList<>();


    @Override
    public Post getById(Long id) {
        return data.stream().
                filter(item -> item.getId().equals(id) && item.getDeletedDate() == null)
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
    }

    @Override
    public List<Post> getByIdIn(List<Long> ids) {
        return data.stream()
                .filter(item -> ids.contains(item.getId()))
                .toList();
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
    public List<PostListQuery> getByCategoryId(Long categoryId, Pageable pageable) {

        if (categoryId == null) {
            return data.stream()
                    .sorted(Comparator.comparing(Post::getId).reversed())
                    .map(item -> new PostListQuery(item.getId(), item.getTitle().title(), item.getContent().content(), item.getView()))
                    .skip(pageable.getOffset() * pageable.getPageSize())
                    .limit(pageable.getPageSize())
                    .collect(Collectors.toList());
        }
        return data.stream()
                .filter(item -> Objects.equals(item.getCategory().getId(), categoryId))
                .sorted(Comparator.comparing(Post::getId).reversed())
                .map(item -> new PostListQuery(item.getId(), item.getTitle().title(), item.getContent().content(), item.getView()))
                .skip((long) pageable.getPageSize() * pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }

    @Override
    public PostQuery getWithCommentById(Long id) {
        if (id == 999999) {
            throw new ResourceNotFoundException("Post", id);
        }
        return PostQuery.builder()
                .id(id)
                .title("title1")
                .content("content1")
                .categoryId(1L)
                .categoryName("category")
                .view(1L)
                .build();
    }

    @Override
    public Integer saveAll(List<Post> updatedPosts) {
        data.addAll(updatedPosts);
        return updatedPosts.size();
    }
}
