package com.example.social_app.repository;

import com.example.social_app.model.Like;
import com.example.social_app.model.Post;
import com.example.social_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);
    Optional<Like> findByUserAndPost(User user, Post post);
    int countByPost(Post post);
}
