package study.wild.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Category extends BaseTimeEntity{
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "category")
    private Post post;
}
