package study.wild.comment.controller.response;

import lombok.Builder;
import lombok.Getter;
import study.wild.comment.infrastructure.CommentQuery;

import java.util.List;

@Getter
public class CommentListResponse {
    List<CommentResponse> commentListResponse;

    @Builder
    private CommentListResponse(List<CommentResponse> commentListResponse) {
        this.commentListResponse = commentListResponse;
    }

    public static CommentListResponse from(List<CommentQuery> comments) {
        if (comments == null) {
            return null;
        }

        return CommentListResponse.builder()
                .commentListResponse(comments.stream()
                        .map(CommentResponse::from)
                        .toList())
                .build();
    }
}
