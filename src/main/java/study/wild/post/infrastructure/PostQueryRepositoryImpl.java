package study.wild.post.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static study.wild.category.infrastructure.QCategoryEntity.categoryEntity;
import static study.wild.post.infrastructure.QPostEntity.postEntity;
import static study.wild.user.infrastructure.QUserEntity.userEntity;

public class PostQueryRepositoryImpl implements PostQueryRepository {
    private final JPAQueryFactory queryFactory;

    public PostQueryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<PostListQuery> findByCategoryId(Long categoryId, Pageable pageable) {
        return queryFactory.select(new QPostListQuery(postEntity.id,
                        postEntity.title,
                        postEntity.content,
                        postEntity.view))
                .from(postEntity)
                .where(categoryIdEqual(categoryId),
                        deletedIsNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postEntity.id.desc())
                .fetch();
    }

    @Override
    public Optional<PostQuery> findWithCommentById(Long id) {
        PostQuery postQuery = queryFactory.select(new QPostQuery(
                        postEntity.id,
                        postEntity.title,
                        postEntity.content,
                        postEntity.view,
                        postEntity.createdDate,
                        categoryEntity.id,
                        categoryEntity.name,
                        userEntity.no,
                        userEntity.name
                )).from(postEntity)
                .join(postEntity.category, categoryEntity)
                .join(postEntity.user, userEntity)
                .where(postIdEqual(id),
                        deletedIsNull())
                .fetchOne();

        return postQuery != null ? Optional.of(postQuery) : Optional.empty();
    }

    @Override
    public Long countByCategoryId(Long categoryId) {
        return queryFactory.select(postEntity.id.count())
                .from(postEntity)
                .where(categoryIdEqual(categoryId))
                .fetchOne();
    }

    private BooleanExpression postIdEqual(Long postId) {
        return postId != null ? postEntity.id.eq(postId) : null;
    }

    private BooleanExpression categoryIdEqual(Long categoryId) {
        return categoryId != null ? postEntity.category.id.eq(categoryId) : null;
    }

    private BooleanExpression deletedIsNull() {
        return postEntity.deletedDate.isNull();
    }
}
