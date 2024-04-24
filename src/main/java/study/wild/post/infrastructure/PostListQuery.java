package study.wild.post.infrastructure;

import com.querydsl.core.annotations.QueryProjection;

public record PostListQuery(Long id, String title, String content, Long view) {
	@QueryProjection
	public PostListQuery {
	}
}
