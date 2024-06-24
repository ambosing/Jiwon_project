package study.wild.user.domain;

import lombok.Builder;

public record UserId(String id) {

    @Builder
    public UserId {
        validate(id);
    }

    private void validate(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id를 입력해주세요.");
        }

        if (id.length() < 4) {
            throw new IllegalArgumentException("id는 4자 이상 입력해주세요.");
        }
    }
}
