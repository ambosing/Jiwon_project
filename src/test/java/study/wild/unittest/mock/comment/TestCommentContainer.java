package study.wild.unittest.mock.comment;

import lombok.Builder;
import study.wild.category.service.port.CategoryRepository;
import study.wild.comment.controller.CommentController;
import study.wild.comment.controller.port.CommentService;
import study.wild.comment.service.CommentServiceImpl;
import study.wild.comment.service.port.CommentRepository;
import study.wild.common.service.DatetimeHolder;
import study.wild.post.service.port.PostRepository;
import study.wild.unittest.mock.category.FakeCategoryRepository;
import study.wild.unittest.mock.post.FakePostRepository;


public class TestCommentContainer {
    public final CategoryRepository categoryRepository;
    public final PostRepository postRepository;
    public final CommentRepository commentRepository;
    public final CommentService commentService;
    public final CommentController commentController;

    @Builder
    public TestCommentContainer(DatetimeHolder datetimeHolder) {
        this.categoryRepository = new FakeCategoryRepository();
        this.postRepository = new FakePostRepository();
        this.commentRepository = new FakeCommentRepository();
        this.commentService = CommentServiceImpl.builder()
                .postRepository(postRepository)
                .commentRepository(commentRepository)
                .datetimeHolder(datetimeHolder)
                .build();
        this.commentController = CommentController.builder()
                .commentService(this.commentService)
                .build();
    }
}
