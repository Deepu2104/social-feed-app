package com.example.social_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDTO {
    private Long followerId;
    private String followerUsername;
    private Long followingId;
    private String followingUsername;
}
