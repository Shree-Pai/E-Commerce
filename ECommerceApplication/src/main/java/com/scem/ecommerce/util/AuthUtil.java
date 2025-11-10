package com.scem.ecommerce.util;

import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
	 @Autowired
    private final UserRepository userRepository;

   
    public AuthUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get the currently logged-in user from JWT
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // email/username from JWT
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
