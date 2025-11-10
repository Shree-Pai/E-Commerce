package com.scem.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.scem.ecommerce.dao.CartRepository;
import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Cart;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.service.impl.CartServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CartServiceTest {

    @Mock
    private CartRepository cartRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private ProductRepository productRepo;
    


    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Product product;
    private Cart cart;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        product = new Product();
        product.setProductId(1L);
        product.setPrice(100.0);
        cart = new Cart();
        cart.setUser(user);
        product.setStock(10); 
    }

    @Test
    void testGetCartByUserId() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepo.findByUser(user)).thenReturn(Optional.of(cart));
        Cart c = cartService.getCartByUserId(1L);
        assertNotNull(c);
        assertEquals(user, c.getUser());
    }

    @Test
    void testAddProductToCart() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(cartRepo.findByUser(user)).thenReturn(Optional.of(cart));
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));
        when(cartRepo.save(cart)).thenReturn(cart);
        
        Cart updated = cartService.addProductToCart(1L, 1L, 2);
        assertEquals(1, updated.getCartItems().size());
    }
}
