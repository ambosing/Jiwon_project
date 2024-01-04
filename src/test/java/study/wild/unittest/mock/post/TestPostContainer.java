package study.wild.unittest.mock.post;

import lombok.Builder;
import study.wild.category.service.port.CategoryRepository;
import study.wild.comment.service.port.CommentRepository;
import study.wild.common.service.DatetimeHolder;
import study.wild.post.controller.PostController;
import study.wild.post.controller.port.PostService;
import study.wild.post.service.PostServiceImpl;
import study.wild.post.service.ViewService;
import study.wild.post.service.port.PostRepository;
import study.wild.unittest.mock.category.FakeCategoryRepository;
import study.wild.unittest.mock.comment.FakeCommentRepository;


public class TestPostContainer {
    public final PostRepository postRepository;
    public final PostService postService;
    public final PostController postController;
    public final CategoryRepository categoryRepository;
    public final CommentRepository commentRepository;
    public final ViewService viewService;

    @Builder
    public TestPostContainer(DatetimeHolder datetimeHolder) {
        this.categoryRepository = new FakeCategoryRepository();
        this.postRepository = new FakePostRepository();
        this.commentRepository = new FakeCommentRepository();
        this.viewService = ViewService.builder()
                .postRepository(this.postRepository)
                .build();
        this.postService = PostServiceImpl.builder()
                .categoryRepository(this.categoryRepository)
                .postRepository(this.postRepository)
                .commentRepository(this.commentRepository)
                .datetimeHolder(datetimeHolder)
                .viewService(this.viewService)
                .build();
        this.postController = PostController.builder()
                .postService(this.postService)
                .build();
    }
}
