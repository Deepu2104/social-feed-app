package com.example.social_app.service;

import com.example.social_app.dto.UserDTO;
import com.example.social_app.enums.Role;
import com.example.social_app.model.User;
import com.example.social_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String registerAdmin(UserDTO adminDTO) {

        // since we don't have any intital admins so disabling this part
//        User requester = userRepository.findById(requesterAdminId)
//                .orElseThrow(() -> new RuntimeException("Requester admin not found"));
//
//        if (!"ADMIN".equals(requester.getRole())) {
//            return "Access denied! Only admins can create other admins.";
//        }

        // or we can manually create the first admin
//        INSERT INTO users (username, password, role)
//        VALUES ('superadmin', '$2a$10$HbuFulHWZrNYqd2RPrfqAen7/2pZDgX2JU0FyV02dp9mMT7FAX3Qa', 'ADMIN');

        User admin = new User();
        admin.setUsername(adminDTO.getUsername());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        admin.setRole(Role.ADMIN); // Explicitly setting role as ADMIN

        userRepository.save(admin);
        return "Admin user created successfully.";
    }

    public String deleteUser(Long adminId, Long userId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.getRole() != Role.ADMIN) {
            return "Access denied! Only admins can delete users.";
        }

        userRepository.deleteById(userId);
        return "User deleted successfully.";
    }
}
