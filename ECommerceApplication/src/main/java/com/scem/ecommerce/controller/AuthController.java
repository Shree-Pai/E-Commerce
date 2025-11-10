package com.scem.ecommerce.controller;

import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.dto.auth.AuthRequest;
import com.scem.ecommerce.dto.auth.AuthResponse;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.service.UserService;
import com.scem.ecommerce.util.JwtTokenUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    @SuppressWarnings("unused")
	private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
   
    private final UserService userService;
    private final JwtTokenUtil jwtUtil; 

    
    public AuthController(AuthenticationManager authManager,PasswordEncoder passwordEncoder, UserRepository userRepository,
 UserService userService,JwtTokenUtil jwtUtil) {
    	
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        User newUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        
     
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        
        
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        
        
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername(), roles));
    }
}