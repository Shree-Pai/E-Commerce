package com.scem.ecommerce.service;

import java.util.List;

import com.scem.ecommerce.entity.Cart;
import com.scem.ecommerce.entity.CartItem;

public interface CartService {
	
	Cart getCartByUserId(Long userId);
    Cart addProductToCart(Long userId, Long productId, int quantity);
    Cart removeProductFromCart(Long userId, Long productId);
    Cart updateProductQuantity(Long userId, Long productId, int quantity);
    void clearCart(Long userId);
    List<CartItem> getCartItems(Long userId);
}
