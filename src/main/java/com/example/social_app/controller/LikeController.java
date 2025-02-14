package com.example.social_app.controller;

import com.example.social_app.dto.LikeDTO;
import com.example.social_app.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestBody LikeDTO likeDTO) {
        return ResponseEntity.ok(likeService.likePost(likeDTO));
    }

    @PostMapping("/unlike")
    public ResponseEntity<String> unlikePost(@RequestBody LikeDTO likeDTO) {
        return ResponseEntity.ok(likeService.unlikePost(likeDTO));
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Integer> countLikes(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.countLikes(postId));
    }
}
