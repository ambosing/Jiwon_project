package study.wild.comment.infrastructure;

import java.util.List;

public interface CommentQueryRepository {
    List<CommentQuery> getWithUserByPostId(Long postId);
}
