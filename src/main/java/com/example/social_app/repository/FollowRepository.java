package com.example.social_app.repository;

import com.example.social_app.model.Follow;
import com.example.social_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    // SELECT * FROM follows WHERE follower_id = ? AND following_id = ?

    List<Follow> findByFollower(User follower);
    // Finds all users that this person is following.
    // This searches the follows table where follower_id = user.id.

    List<Follow> findByFollowing(User following);
    // Finds all users that are following this person.
    // This searches the follows table where following_id = user.id.
}
