package com.example.social_app.controller;

import com.example.social_app.model.BlockedUser;
import com.example.social_app.service.BlockedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/block")
@RequiredArgsConstructor
public class BlockedUserController {
    private final BlockedUserService blockedUserService;

    // Block a user
    @PostMapping("/{blockerId}/{blockedId}")
    public ResponseEntity<String> blockUser(@PathVariable Long blockerId, @PathVariable Long blockedId) {
        return ResponseEntity.ok(blockedUserService.blockUser(blockerId, blockedId));
    }

    // Unblock a user
    @DeleteMapping("/{blockerId}/{blockedId}")
    public ResponseEntity<String> unblockUser(@PathVariable Long blockerId, @PathVariable Long blockedId) {
        return ResponseEntity.ok(blockedUserService.unblockUser(blockerId, blockedId));
    }

    // Get list of blocked users
    @GetMapping("/{blockerId}")
    public ResponseEntity<List<BlockedUser>> getBlockedUsers(@PathVariable Long blockerId) {
        return ResponseEntity.ok(blockedUserService.getBlockedUsers(blockerId));
    }
}
