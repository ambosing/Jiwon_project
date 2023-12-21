package study.wild.post.domain;

import lombok.Builder;

@Builder
public record PostContent(String content) {
    public PostContent {
        validateEmptyString(content);
    }

    private void validateEmptyString(String content) {
        if (content == null || content.isEmpty() || content.isBlank()) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }
    }
}
