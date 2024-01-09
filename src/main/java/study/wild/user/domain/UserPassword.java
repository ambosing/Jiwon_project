package study.wild.user.domain;

import lombok.Builder;

public record UserPassword(String password) {

    @Builder
    public UserPassword {
        validate(password);
    }

    private void validate(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 입력해주세요.");
        }
    }
}
