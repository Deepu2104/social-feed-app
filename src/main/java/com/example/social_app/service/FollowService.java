package com.example.social_app.service;

import com.example.social_app.dto.FollowDTO;
import com.example.social_app.model.Follow;
import com.example.social_app.model.User;
import com.example.social_app.repository.FollowRepository;
import com.example.social_app.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.social_app.enums.ActivityType;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final ActivityService activityService;


    public FollowService(FollowRepository followRepository, UserRepository userRepository, ActivityService activityService) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.activityService = activityService;
    }

    public String followUser(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            return "You cannot follow yourself!";
        }

        User follower = userRepository.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingId).orElseThrow(() -> new RuntimeException("User to follow not found"));

        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(follower, following);
        if (existingFollow.isPresent()) {
            return "You are already following this user!";
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);
        activityService.logActivity(follower.getId(), ActivityType.FOLLOW, following.getId());

        return "Followed successfully!";
    }

    public String unfollowUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingId).orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(follower, following);
        if (existingFollow.isPresent()) {
            followRepository.delete(existingFollow.get());
            return "Unfollowed successfully!";
        }
        return "You are not following this user!";
    }

    public List<FollowDTO> getFollowers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Follow> followers = followRepository.findByFollowing(user);

        return followers.stream().map(follow -> {
            FollowDTO dto = new FollowDTO();
            dto.setFollowerId(follow.getFollower().getId());
            dto.setFollowerUsername(follow.getFollower().getUsername());
            dto.setFollowingId(userId);
            dto.setFollowingUsername(user.getUsername());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<FollowDTO> getFollowing(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Follow> following = followRepository.findByFollower(user);

        return following.stream().map(follow -> {
            FollowDTO dto = new FollowDTO();
            dto.setFollowerId(userId);
            dto.setFollowerUsername(user.getUsername());
            dto.setFollowingId(follow.getFollowing().getId());
            dto.setFollowingUsername(follow.getFollowing().getUsername());
            return dto;
        }).collect(Collectors.toList());
    }
}
