package study.wild.unittest.mock.comment;

import study.wild.comment.domain.Comment;
import study.wild.comment.service.port.CommentRepository;
import study.wild.common.domain.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FakeCommentRepository implements CommentRepository {
    private Long id = 0L;
    private final List<Comment> data = new ArrayList<>();

    @Override
    public Comment getById(Long id) {
        return data.stream()
                .filter(item -> Objects.equals(item.getId(), id) && item.getDeletedDate() == null)
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null || comment.getId() == 0) {
            Comment newComment = Comment.builder()
                    .id(++id)
                    .content(comment.getContent().content())
                    .post(comment.getPost())
                    .deletedDate(comment.getDeletedDate())
                    .build();
            data.add(newComment);
            return newComment;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), comment.getId()));
            data.add(comment);
            return comment;
        }
    }

}
