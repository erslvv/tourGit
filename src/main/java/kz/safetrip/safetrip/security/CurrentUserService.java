package kz.safetrip.safetrip.security;

import kz.safetrip.safetrip.model.entity.User;
import kz.safetrip.safetrip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserRepository userRepository;
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) throw new IllegalStateException("Authenticated user not found");
        return userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }
    public Long getCurrentUserId() { return getCurrentUser().getId(); }
}
