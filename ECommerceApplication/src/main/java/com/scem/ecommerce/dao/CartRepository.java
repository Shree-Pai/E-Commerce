package com.scem.ecommerce.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scem.ecommerce.entity.Cart;
import com.scem.ecommerce.entity.User;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Optional<Cart>  findByUser(User user);

}
