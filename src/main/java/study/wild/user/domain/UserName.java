package study.wild.user.domain;

import lombok.Builder;

@Builder
public record UserName(String name) {

    public UserName {
        validateEmptyString(name);
    }

    private void validateEmptyString(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
    }
}
