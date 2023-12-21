package study.wild.post.domain;

import lombok.Builder;


@Builder
public record PostTitle(String title) {

    public PostTitle {
        validateEmptyString(title);
    }

    private void validateEmptyString(String title) {
        if (title == null || title.isEmpty() || title.isBlank()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }
    }

}
