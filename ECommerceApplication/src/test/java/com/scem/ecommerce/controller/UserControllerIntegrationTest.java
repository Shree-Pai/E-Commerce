package com.scem.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scem.ecommerce.entity.Role;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.entity.enums.RoleName;
import com.scem.ecommerce.security.JwtAuthenticationFilter;
import com.scem.ecommerce.service.UserService;
import com.scem.ecommerce.util.AuthUtil;
import com.scem.ecommerce.util.JwtTokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
	@MockBean
    private UserService userService;

    @SuppressWarnings("removal")
	@MockBean
    private AuthUtil authUtil;
    
    @SuppressWarnings("removal")
	@MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @SuppressWarnings("removal")
	@MockBean
    private JwtTokenUtil jwtTokenUtil;

    private ObjectMapper mapper = new ObjectMapper();

    private User adminUser;
    private User normalUser;

    @BeforeEach
    void setup() {
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ROLE_ADMIN);

        Role userRole = new Role();
        userRole.setRoleName(RoleName.ROLE_CUSTOMER);

        adminUser = new User();
        adminUser.setUserId(999L);
        adminUser.setEmail("admin@example.com");
        adminUser.setRoles(Collections.singleton(adminRole));

        normalUser = new User();
        normalUser.setUserId(1L);
        normalUser.setEmail("user@example.com");
        normalUser.setRoles(Collections.singleton(userRole));
    }

    @Test
    void testGetAllUsers_AsAdmin() throws Exception {
        List<User> users = Collections.singletonList(normalUser);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("user@example.com"));
    }

    @Test
    void testGetUserByIdAdmin_AsAdmin() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(normalUser));

        mockMvc.perform(get("/api/users/admin/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }

    @Test
    void testDeleteUser_AsAdmin() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/admin/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetMyProfile_AsUser() throws Exception {
        Mockito.when(authUtil.getCurrentUser()).thenReturn(normalUser);
        Mockito.when(userService.getUserById(normalUser.getUserId())).thenReturn(Optional.of(normalUser));

        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }

    @Test
    void testGetMyProfile_UserNotFound() throws Exception {
        Mockito.when(authUtil.getCurrentUser()).thenReturn(normalUser);
        Mockito.when(userService.getUserById(normalUser.getUserId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateMyProfile_AsUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setFirstName("NewName");
        updatedUser.setEmail("user@example.com");

        // Correct way to set roles:
        Role userRole = new Role();
        userRole.setRoleName(RoleName.ROLE_CUSTOMER);
        updatedUser.setRoles(Collections.singleton(userRole));

        Mockito.when(authUtil.getCurrentUser()).thenReturn(normalUser);
        Mockito.when(userService.updateUser(eq(normalUser.getUserId()), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("NewName"));
    }
}
