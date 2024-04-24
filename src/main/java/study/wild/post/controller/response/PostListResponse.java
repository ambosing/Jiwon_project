package study.wild.post.controller.response;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import lombok.Builder;
import lombok.Getter;
import study.wild.post.infrastructure.PostListQuery;

@Getter
public class PostListResponse {
	private final Long id;
	private final String title;
	private final String content;
	private final Long view;

	@Builder
	private PostListResponse(PostListQuery postListQuery) {
		this.id = postListQuery.id();
		this.title = postListQuery.title();
		this.content = postListQuery.content();
		this.view = postListQuery.view();
	}

	public static Page<PostListResponse> from(Page<PostListQuery> posts) {
		List<PostListResponse> postListResponses = posts.getContent().stream()
			.map(PostListResponse::new)
			.toList();

		return new PageImpl<>(postListResponses, posts.getPageable(), posts.getTotalElements());
	}
}
