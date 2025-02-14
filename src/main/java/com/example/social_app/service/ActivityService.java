package com.example.social_app.service;

import com.example.social_app.dto.ActivityDTO;
import com.example.social_app.enums.ActivityType;
import com.example.social_app.model.Activity;
import com.example.social_app.model.User;
import com.example.social_app.repository.ActivityRepository;
import com.example.social_app.repository.PostRepository;
import com.example.social_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void logActivity(Long userId, ActivityType type, Long entityId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Activity activity = new Activity();
        activity.setUser(user);
        activity.setType(type);
        activity.setEntityId(entityId);
        activity.setTimestamp(LocalDateTime.now());

        activityRepository.save(activity);
    }

    // Fetch global activity feed with readable messages
    public List<ActivityDTO> getGlobalActivityFeed() {
        List<Activity> activities = activityRepository.findAllByOrderByTimestampDesc();
        return activities.stream().map(this::convertToDTO).toList();
    }

    private ActivityDTO convertToDTO(Activity activity) {
        String message = generateMessage(activity);
        return new ActivityDTO(activity.getUser().getUsername(), activity.getType(), message, activity.getTimestamp());
    }

    private String generateMessage(Activity activity) {
        String username = activity.getUser().getUsername();
        Long entityId = activity.getEntityId();

        return switch (activity.getType()) {
            case FOLLOW -> userRepository.findById(entityId)
                    .map(followedUser -> username + " started following " + followedUser.getUsername())
                    .orElse(username + " followed an unknown user");

            case POST -> username + " created a new post";

            case LIKE -> postRepository.findById(entityId)
                    .map(post -> username + " liked Post " + post.getId())
                    .orElse(username + " liked an unknown post");
        };
    }
}

