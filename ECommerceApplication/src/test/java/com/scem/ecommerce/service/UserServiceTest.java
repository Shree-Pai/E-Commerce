package com.scem.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepo.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
    }

    @Test
    void testGetUserById() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> found = userService.getUserById(1L);
        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
    }
}
