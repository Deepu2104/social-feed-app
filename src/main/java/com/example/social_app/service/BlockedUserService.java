package com.example.social_app.service;

import com.example.social_app.model.BlockedUser;
import com.example.social_app.model.User;
import com.example.social_app.repository.BlockedUserRepository;
import com.example.social_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockedUserService {
    private final BlockedUserRepository blockedUserRepository;
    private final UserRepository userRepository;

    public String blockUser(Long blockerId, Long blockedId) {
        if (blockerId.equals(blockedId)) {
            return "You cannot block yourself!";
        }

        User blocker = userRepository.findById(blockerId)
                .orElseThrow(() -> new RuntimeException("Blocker not found"));
        User blocked = userRepository.findById(blockedId)
                .orElseThrow(() -> new RuntimeException("Blocked user not found"));

        if (blockedUserRepository.existsByBlockerAndBlocked(blocker, blocked)) {
            return "User is already blocked!";
        }

        BlockedUser blockedUser = new BlockedUser();
        blockedUser.setBlocker(blocker);
        blockedUser.setBlocked(blocked);
        blockedUser.setTimestamp(LocalDateTime.now());

        blockedUserRepository.save(blockedUser);
        return "User blocked successfully";
    }

    public String unblockUser(Long blockerId, Long blockedId) {
        User blocker = userRepository.findById(blockerId)
                .orElseThrow(() -> new RuntimeException("Blocker not found"));
        User blocked = userRepository.findById(blockedId)
                .orElseThrow(() -> new RuntimeException("Blocked user not found"));

        Optional<BlockedUser> blockedUser = blockedUserRepository.findByBlockerAndBlocked(blocker, blocked);
        if (blockedUser.isPresent()) {
            blockedUserRepository.delete(blockedUser.get());
            return "User unblocked successfully";
        }
        return "User was not blocked!";
    }

    public boolean isBlocked(Long blockerId, Long blockedId) {
        User blocker = userRepository.findById(blockerId).orElse(null);
        User blocked = userRepository.findById(blockedId).orElse(null);
        return blocker != null && blocked != null && blockedUserRepository.existsByBlockerAndBlocked(blocker, blocked);
    }

    public List<BlockedUser> getBlockedUsers(Long blockerId) {
        User blocker = userRepository.findById(blockerId)
                .orElseThrow(() -> new RuntimeException("Blocker not found"));
        return blockedUserRepository.findAllByBlocker(blocker);
    }
}
