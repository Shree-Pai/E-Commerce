package com.scem.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scem.ecommerce.dao.RoleRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Role;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.entity.enums.RoleName;
import com.scem.ecommerce.exception.UserNotFoundException;
import com.scem.ecommerce.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        Role userRole = roleRepo.findByRoleName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        user.getRoles().add(userRole);

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepo.save(user);
        logger.info("Added new user [{}] with email [{}]", saved.getUserId(), saved.getEmail());
        return saved;
    }


    @Override
    public Optional<User> getUserById(Long userId) {
        logger.debug("Fetching user by id [{}]", userId);
        return userRepo.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        logger.debug("Fetching user by email [{}]", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        logger.debug("Fetching all users");
        return (List<User>) userRepo.findAll();
    }

    @Override
    public User updateUser(Long userId, User user) {
        User existingUser = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setMobileNumber(user.getMobileNumber());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        User updated = userRepo.save(existingUser);
        logger.info("Updated user [{}] profile", userId);
        return updated;
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepo.existsById(userId)) {
            logger.warn("Attempt to delete non-existing user [{}]", userId);
            throw new UserNotFoundException("User not found: " + userId);
        }
        userRepo.deleteById(userId);
        logger.info("Deleted user [{}]", userId);
    }

}
