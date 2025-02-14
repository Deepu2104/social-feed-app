package com.example.social_app.service;

import com.example.social_app.dto.PostDTO;
import com.example.social_app.enums.ActivityType;
import com.example.social_app.model.Post;
import com.example.social_app.model.User;
import com.example.social_app.repository.PostRepository;
import com.example.social_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ActivityService activityService;

    public PostService(PostRepository postRepository, UserRepository userRepository, ActivityService activityService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.activityService = activityService;
    }

    public String createPost(PostDTO postDTO) {
        Optional<User> user = userRepository.findById(postDTO.getUserId());
        if (user.isPresent()) {
            Post post = new Post();
            post.setUser(user.get());
            post.setContent(postDTO.getContent());
            postRepository.save(post);
            activityService.logActivity(user.get().getId(), ActivityType.POST, post.getId());
            return "Post created successfully";
        }
        return "User not found";
    }

    public List<Post> getUserPosts(Long userId) {
        // todo we can check if user here exists or not and throw the exception
        return postRepository.findByUserId(userId);
    }
}
