package com.example.social_app.service;

import com.example.social_app.dto.LikeDTO;
import com.example.social_app.model.Like;
import com.example.social_app.model.Post;
import com.example.social_app.model.User;
import com.example.social_app.repository.LikeRepository;
import com.example.social_app.repository.PostRepository;
import com.example.social_app.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.social_app.enums.ActivityType;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ActivityService activityService;


    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository, ActivityService activityService) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.activityService = activityService;
    }

    public String likePost(LikeDTO likeDTO) {
        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (likeRepository.existsByUserAndPost(user, post)) {
            return "You already liked this post!";
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setCreatedAt(LocalDateTime.now().toString());

        likeRepository.save(like);
        activityService.logActivity(user.getId(), ActivityType.LIKE, post.getId());
        return "Post liked successfully!";
    }

    public String unlikePost(LikeDTO likeDTO) {
        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Like> like = likeRepository.findByUserAndPost(user, post);
        if (like.isEmpty()) {
            return "You haven't liked this post!";
        }

        likeRepository.delete(like.get());
        return "Post unliked successfully!";
    }

    public int countLikes(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return likeRepository.countByPost(post);
    }
}
