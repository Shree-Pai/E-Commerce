package com.scem.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scem.ecommerce.dao.CartItemRepository;
import com.scem.ecommerce.dao.CartRepository;
import com.scem.ecommerce.dao.ProductRepository;
import com.scem.ecommerce.dao.UserRepository;
import com.scem.ecommerce.entity.Cart;
import com.scem.ecommerce.entity.CartItem;
import com.scem.ecommerce.entity.Product;
import com.scem.ecommerce.entity.User;
import com.scem.ecommerce.exception.ProductNotFoundException;
import com.scem.ecommerce.exception.UserNotFoundException;
import com.scem.ecommerce.service.CartService;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    @Transactional
    public Cart getCartByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });

        // Refresh cart item prices in memory only
        cart.getCartItems().forEach(item ->
            item.setProductPrice(item.getProduct().getPrice())
        );

        updateCartTotal(cart);
        return cart;
    }


    @Override
    @Transactional
    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        // Check if the item already exists in the cart
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        int newQuantity = (existingItem != null ? existingItem.getQuantity() : 0) + quantity;

        if (newQuantity > product.getStock()) {
            throw new IllegalStateException("Requested quantity exceeds available stock");
        }

        if (existingItem != null) {
            existingItem.setQuantity(newQuantity);
            // Update unit price only if product price changed
            if (!existingItem.getProductPrice().equals(product.getPrice())) {
                existingItem.setProductPrice(product.getPrice());
            }
            cartItemRepo.save(existingItem);
        } else {
            CartItem newItem = new CartItem(cart, product, quantity, product.getPrice());
            cart.getCartItems().add(newItem);
            cartItemRepo.save(newItem);
        }


        updateCartTotal(cart);
        return cartRepo.save(cart);
    }

    private void updateCartTotal(Cart cart) {
        double total = 0.0;
        for (CartItem item : cart.getCartItems()) {
            total += item.getQuantity() * item.getProductPrice(); // use stored unit price
        }
        cart.setTotalPrice(total);
    }


    @Override
    public Cart removeProductFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        cart.getCartItems().removeIf(item -> item.getProduct().getProductId().equals(productId));
        updateCartTotal(cart);
        return cartRepo.save(cart);
    }

    @Override
    public Cart updateProductQuantity(Long userId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        Cart cart = getCartByUserId(userId);
        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Cart item not found"));

        Product product = item.getProduct();
        if (quantity > product.getStock()) {
            throw new IllegalStateException("Requested quantity exceeds available stock");
        }

        item.setQuantity(quantity);
        item.setProductPrice(product.getPrice());
        // update price
        cartItemRepo.save(item);
        updateCartTotal(cart);
        return cartRepo.save(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getCartItems().clear();
        updateCartTotal(cart);
        cartRepo.save(cart);
    }

    @Override
    	public List<CartItem> getCartItems(Long userId) {
    	    Cart cart = getCartByUserId(userId);
    	    // Do NOT overwrite item price here
    	    updateCartTotal(cart);
    	    return cart.getCartItems();
    	}

    }
