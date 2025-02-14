package com.example.social_app.dto;

import com.example.social_app.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ActivityDTO {
    private String username;
    private ActivityType type;
    private String message;
    private LocalDateTime timestamp;
}
