package study.wild.post.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long>, PostQueryRepository {
    Optional<PostEntity> findByIdAndDeletedDateIsNull(Long id);

}
