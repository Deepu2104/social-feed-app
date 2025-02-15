package com.example.social_app.service;

import com.example.social_app.enums.Role;
import com.example.social_app.model.User;
import com.example.social_app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signup(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Username already taken";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // If no owners exist, make this user an OWNER
        boolean ownerExists = userRepository.existsByRole(Role.OWNER);
        user.setRole(ownerExists ? Role.USER : Role.OWNER);

//        user.setRole(Role.USER);
        userRepository.save(user);
        return "Signup successful!";
    }

    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}
