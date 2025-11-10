package com.scem.ecommerce.config;

import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.scem.ecommerce.dao.RoleRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Role;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.entity.enums.RoleName;

@Configuration
public class AdminSeeder {

    /** Seeds roles and a default admin user on application startup **/
    @SuppressWarnings("unused")
	@Bean
    CommandLineRunner initAdmin(UserRepository userRepository,
                             RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder) {
	    return args -> {
	        /* Retrieves Admin Role */
	        Role adminRole = roleRepository.findByRoleName(RoleName.ROLE_ADMIN)
	                .orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_ADMIN)));

	        /* Retrieves User Role */
	        roleRepository.findByRoleName(RoleName.ROLE_CUSTOMER)
	                .orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_CUSTOMER)));

	        String adminEmail = "admin@admin.com";

	        if (!userRepository.findByEmail(adminEmail).isPresent()) {
	            User admin = new User();
	            admin.setFirstName("System");
	            admin.setLastName("Admin");
	            admin.setMobileNumber("9999999999");
	            admin.setEmail(adminEmail);
	            admin.setPassword(passwordEncoder.encode("admin123"));
	            admin.setRoles(new HashSet<>());
	            // Assign the managed Role entity here
	            admin.getRoles().add(adminRole);

	            userRepository.save(admin);
	            
	        } else {
	            System.out.println(">>> Admin account : " + adminEmail);
	        }
	    };
	}

}
