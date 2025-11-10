package com.scem.ecommerce.controller;

import com.scem.ecommerce.dto.CartItemRequest;
import com.scem.ecommerce.entity.Cart;
import com.scem.ecommerce.entity.CartItem;
import com.scem.ecommerce.service.CartService;
import com.scem.ecommerce.util.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/cart")
public class CartController {
	 @Autowired
      private final CartService cartService;
	 @Autowired
      private final AuthUtil authUtil;

   
    public CartController(CartService cartService, AuthUtil authUtil) {
        this.cartService = cartService;
        this.authUtil = authUtil;
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CartItem>> getCartItems() {
        Long userId = authUtil.getCurrentUser().getUserId();
        List<CartItem> items = cartService.getCartItems(userId);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Cart> addToCart(@RequestBody CartItemRequest request) {
        Long userId = authUtil.getCurrentUser().getUserId();
        Cart cart = cartService.addProductToCart(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{productId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long productId) {
        Long userId = authUtil.getCurrentUser().getUserId();
        Cart cart = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Cart> updateQuantity(@RequestBody CartItemRequest request) {
        Long userId = authUtil.getCurrentUser().getUserId();
        Cart cart = cartService.updateProductQuantity(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> clearCart() {
        Long userId = authUtil.getCurrentUser().getUserId();
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}


