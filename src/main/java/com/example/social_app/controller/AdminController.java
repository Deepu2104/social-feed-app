package com.example.social_app.controller;

import com.example.social_app.dto.UserDTO;
import com.example.social_app.service.LikeService;
import com.example.social_app.service.PostService;
import com.example.social_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;

    @DeleteMapping("/delete-user/{adminId}/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long adminId, @PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteUser(adminId, userId));
    }

    @DeleteMapping("/delete-post/{adminId}/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long adminId, @PathVariable Long postId) {
        return ResponseEntity.ok(postService.deletePost(adminId, postId));
    }

    @DeleteMapping("/delete-like/{adminId}/{likeId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long adminId, @PathVariable Long likeId) {
        return ResponseEntity.ok(likeService.deleteLike(adminId, likeId));
    }

    @PostMapping("/promote/{ownerId}/{userId}")
    public ResponseEntity<String> promoteToAdmin(@PathVariable Long ownerId, @PathVariable Long userId) {
        return ResponseEntity.ok(userService.promoteToAdmin(ownerId, userId));
    }

    @PostMapping("/demote/{ownerId}/{adminId}")
    public ResponseEntity<String> demoteAdmin(@PathVariable Long ownerId, @PathVariable Long adminId) {
        return ResponseEntity.ok(userService.demoteAdmin(ownerId, adminId));
    }

}
