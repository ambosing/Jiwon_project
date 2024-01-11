package study.wild.comment.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static study.wild.comment.infrastructure.QCommentEntity.commentEntity;
import static study.wild.user.infrastructure.QUserEntity.userEntity;

public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CommentQueryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<CommentQuery> getWithUserByPostId(Long postId) {
        return queryFactory.select(new QCommentQuery(
                        commentEntity.id,
                        commentEntity.content,
                        userEntity.no,
                        userEntity.id,
                        commentEntity.createdDate,
                        commentEntity.lastModifiedDate
                )).from(commentEntity)
                .join(commentEntity.user, userEntity)
                .where(postIdEqual(postId),
                        deletedIsNull())
                .fetch();
    }

    private BooleanExpression postIdEqual(Long postId) {
        return postId == null ? null : commentEntity.post.id.eq(postId);
    }

    private BooleanExpression deletedIsNull() {
        return commentEntity.deletedDate.isNull();
    }

}
