package com.scem.ecommerce.controller;

import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.exception.UserNotFoundException;
import com.scem.ecommerce.service.UserService;
import com.scem.ecommerce.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private final AuthUtil authUtil;

 
    public UserController(UserService userService, AuthUtil authUtil) {
        this.userService = userService;
        this.authUtil = authUtil;
    }

    /** ADMIN Routes**/

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

 
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByIdAdmin(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return ResponseEntity.ok(user.get());
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /** USER Routes**/
    
    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<User> getMyProfile() {
        Long userId = authUtil.getCurrentUser().getUserId();
        User user = userService.getUserById(userId)
                       .orElseThrow(() -> new UserNotFoundException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<User> updateMyProfile(@RequestBody User updatedUser) {
        User currentUser = authUtil.getCurrentUser();  
        Long userId = currentUser.getUserId();              
        User user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }

}
