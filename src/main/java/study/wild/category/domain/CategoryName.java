package study.wild.category.domain;

import lombok.Builder;

@Builder
public record CategoryName(String name) {

    public CategoryName {
        validate(name);
    }

    private void validate(String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
    }
}
