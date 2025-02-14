package com.example.social_app.repository;

import com.example.social_app.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // Fetch all activities sorted by timestamp (latest first)
    List<Activity> findAllByOrderByTimestampDesc();
}
