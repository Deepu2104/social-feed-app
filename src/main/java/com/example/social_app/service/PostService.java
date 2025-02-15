package com.example.social_app.service;

import com.example.social_app.dto.PostDTO;
import com.example.social_app.enums.ActivityType;
import com.example.social_app.enums.Role;
import com.example.social_app.model.Post;
import com.example.social_app.model.User;
import com.example.social_app.repository.BlockedUserRepository;
import com.example.social_app.repository.PostRepository;
import com.example.social_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ActivityService activityService;
    private final BlockedUserRepository blockedUserRepository;


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

    public List<Post> getUserFeed(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Long> blockedUserIds = blockedUserRepository.findAllByBlocker(user)
                .stream()
                .map(blockedUser -> blockedUser.getBlocked().getId())
                .collect(Collectors.toList());

        return postRepository.findAll().stream()
                .filter(post -> !blockedUserIds.contains(post.getUser().getId()))
                .collect(Collectors.toList());
    }

    public String deletePost(Long adminId, Long postId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.getRole() != Role.ADMIN) {
            return "Access denied! Only admins can delete posts.";
        }

        postRepository.deleteById(postId);
        return "Post deleted successfully.";
    }

}
