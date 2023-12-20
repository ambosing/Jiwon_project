package study.wild.post.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.category.domain.Category;
import study.wild.category.service.port.CategoryRepository;
import study.wild.common.service.DatetimeHolder;
import study.wild.post.controller.port.PostService;
import study.wild.post.domain.Post;
import study.wild.post.domain.PostCreate;
import study.wild.post.domain.PostUpdate;
import study.wild.post.service.port.PostRepository;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final DatetimeHolder datetimeHolder;

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    @Transactional
    public Post create(PostCreate postCreate) {
        Category category = categoryRepository.getById(postCreate.getCategoryId());
        return postRepository.save(Post.fromCreate(category, postCreate));
    }

    @Transactional
    public Post update(Long id, PostUpdate postUpdate) {
        return null;
    }

    @Transactional
    public Long delete(Long id) {
        return null;
    }
}
