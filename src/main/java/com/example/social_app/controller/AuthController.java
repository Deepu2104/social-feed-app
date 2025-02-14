package com.example.social_app.controller;

import com.example.social_app.dto.UserDTO;
import com.example.social_app.model.User;
import com.example.social_app.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.signup(userDTO.getUsername(), userDTO.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        Optional<User> user = authService.login(userDTO.getUsername(), userDTO.getPassword());
        if (user.isPresent()) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid credentials") ;
    }
}
