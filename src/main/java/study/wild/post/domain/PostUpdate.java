package study.wild.post.domain;

import lombok.Builder;

public record PostUpdate(String title, String content, Long categoryId) {

    @Builder
    public PostUpdate {
    }
}
