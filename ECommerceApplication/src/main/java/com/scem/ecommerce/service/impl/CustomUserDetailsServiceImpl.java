package com.scem.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Role;
import com.scem.ecommerce.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	  @Autowired
    private final UserRepository userRepo;

  
    public CustomUserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo; 
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(Role::getRoleName)        
                .map(Enum::name)               
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
