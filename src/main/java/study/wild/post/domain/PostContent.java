package study.wild.post.domain;

import lombok.Builder;

public record PostContent(String content) {

    @Builder
    public PostContent {
        validateEmptyString(content);
    }

    private void validateEmptyString(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }
    }
}
