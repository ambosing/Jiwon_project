package study.wild.post.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.post.domain.Post;
import study.wild.post.service.port.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ViewService {

    private final Map<Long, AtomicInteger> viewCache = new ConcurrentHashMap<>();
    private final PostRepository postRepository;

    public void incrementView(Long postId) {
        viewCache.computeIfAbsent(postId, value -> new AtomicInteger(0)).incrementAndGet();
    }

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void batchUpdateView() {
        List<Long> postIds = viewCache.keySet().stream().toList();
        List<Post> posts = postRepository.getByIdIn(postIds);
        List<Post> updatedPosts = new ArrayList<>();
        posts.forEach(post -> {
            AtomicInteger view = viewCache.get(post.getId());
            Post updatedPost = post.updateView(view.longValue());
            postRepository.save(updatedPost);
        });
        postRepository.saveAll(updatedPosts);
        viewCache.clear();
    }
}
