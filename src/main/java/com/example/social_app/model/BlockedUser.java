package com.example.social_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "blocked_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blocker_id", nullable = false)
    private User blocker; // The user who is blocking

    @ManyToOne
    @JoinColumn(name = "blocked_id", nullable = false)
    private User blocked; // The user who is being blocked

    private LocalDateTime timestamp; // When the block happened
}
