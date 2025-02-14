package com.example.social_app.repository;

import com.example.social_app.model.BlockedUser;
import com.example.social_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
    boolean existsByBlockerAndBlocked(User blocker, User blocked);
    Optional<BlockedUser> findByBlockerAndBlocked(User blocker, User blocked);
    List<BlockedUser> findAllByBlocker(User blocker);
}
