package study.wild.post.domain;

import lombok.Builder;
import study.wild.category.infrastructure.CategoryEntity;

public record PostUpdate(String title, String content, CategoryEntity category) {

    @Builder
    public PostUpdate {
    }
}
