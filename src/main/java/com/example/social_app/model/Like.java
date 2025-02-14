package com.example.social_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key referencing "users" table
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // Foreign key referencing "posts" table
    private Post post;

    private String createdAt;
}
