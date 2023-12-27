package study.wild.post.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import study.wild.post.controller.response.PostListResponse;
import study.wild.post.controller.response.QPostListResponse;

import java.util.List;

import static study.wild.post.infrastructure.QPostEntity.postEntity;

public class PostQueryRepositoryImpl implements PostQueryRepository {
    private final JPAQueryFactory queryFactory;

    public PostQueryRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<PostListResponse> findByCategoryId(Long categoryId, Pageable pageable) {
        return queryFactory.select(new QPostListResponse(postEntity.id,
                        postEntity.title,
                        postEntity.content,
                        postEntity.view))
                .from(postEntity)
                .where(categoryIdEqual(categoryId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postEntity.id.desc())
                .fetch();
    }

    @Override
    public Long countByCategoryId(Long categoryId) {
        return queryFactory.select(postEntity.id.count())
                .from(postEntity)
                .where(categoryIdEqual(categoryId))
                .fetchOne();
    }

    private BooleanExpression categoryIdEqual(Long categoryId) {
        return categoryId != null ? postEntity.category.id.eq(categoryId) : null;
    }
}
