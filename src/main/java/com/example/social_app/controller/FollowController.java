package com.example.social_app.controller;

import com.example.social_app.dto.FollowDTO;
import com.example.social_app.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> followUser(@RequestParam Long followerId, @RequestParam Long followingId) {
        return ResponseEntity.ok(followService.followUser(followerId, followingId));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> unfollowUser(@RequestParam Long followerId, @RequestParam Long followingId) {
        return ResponseEntity.ok(followService.unfollowUser(followerId, followingId));
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<FollowDTO>> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<FollowDTO>> getFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowing(userId));
    }
}
