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


    public String deleteUser(Long adminId, Long userId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.getRole() != Role.ADMIN && admin.getRole() != Role.OWNER) {
            return "Access denied! Only admins or owners can delete users.";
        }

        userRepository.deleteById(userId);
        return "User deleted successfully.";
    }

    public String promoteToAdmin(Long ownerId, Long userId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        if (owner.getRole() != Role.OWNER) {
            return "Access denied! Only owners can promote users to admins.";
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            return "User is already an admin!";
        }

        if(user.getRole() == Role.OWNER && userId.equals(ownerId)){
            return "You can demote owmer to admin. No one can!";
        }

        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return "User promoted to Admin successfully!";
    }

    public String demoteAdmin(Long ownerId, Long adminId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        if (owner.getRole() != Role.OWNER) {
            return "Access denied! Only owners can demote admins.";
        }

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.getRole() != Role.ADMIN) {
            return "User is not an admin!";
        }

        admin.setRole(Role.USER);
        userRepository.save(admin);
        return "Admin demoted to User successfully!";
    }
}
