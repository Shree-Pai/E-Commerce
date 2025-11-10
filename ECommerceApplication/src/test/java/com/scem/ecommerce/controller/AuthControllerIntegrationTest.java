package com.scem.ecommerce.controller;

import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.dto.auth.AuthRequest;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.service.UserService;
import com.scem.ecommerce.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
	@MockBean
    private UserRepository userRepository;

    @SuppressWarnings("removal")
	@MockBean
    private JwtTokenUtil jwtTokenUtil;

    @SuppressWarnings("removal")
	@MockBean
    private AuthenticationManager authManager;

    @SuppressWarnings("removal")
	@MockBean
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("removal")
	@MockBean
    private UserService userService;

    private UserDetails testUserDetails;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setEmail("user@example.com");
        testUser.setPassword("password");

        testUserDetails = org.springframework.security.core.userdetails.User
                .withUsername(testUser.getEmail())
                .password(testUser.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

    @Test
    void testLogin() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(testUser.getEmail());
        authRequest.setPassword(testUser.getPassword());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            testUserDetails, testUser.getPassword(), testUserDetails.getAuthorities()
        );
        Mockito.when(authManager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(jwtTokenUtil.generateToken(testUserDetails)).thenReturn("mocked_jwt_token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("mocked_jwt_token"))
            .andExpect(jsonPath("$.email").value(testUser.getEmail()))
            .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    void testRegister() throws Exception {
        Mockito.when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        Mockito.when(userService.addUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value(testUser.getEmail()));
    }
}
