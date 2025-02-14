package com.example.social_app.controller;

import com.example.social_app.dto.ActivityDTO;
import com.example.social_app.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping("/feed")
    public ResponseEntity<List<ActivityDTO>> getGlobalActivityFeed() {
        return ResponseEntity.ok(activityService.getGlobalActivityFeed());
    }
}

