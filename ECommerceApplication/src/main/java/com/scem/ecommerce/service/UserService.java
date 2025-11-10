package com.scem.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.scem.ecommerce.entity.User;

public interface UserService {
	
	User addUser(User user);
	Optional<User> getUserById(Long userId);
	Optional<User> getUserByEmail(String email);
	List<User> getAllUsers();
	User updateUser(Long userId,User user);
	void deleteUser(Long userId);
	
	
	

}
