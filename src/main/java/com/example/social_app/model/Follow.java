package com.example.social_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_followers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower; // The user who follows

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following; // The user being followed


}
