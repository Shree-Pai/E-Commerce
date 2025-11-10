package com.scem.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore 
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    @JsonIgnore 
    private List<CartItem> cartItems = new ArrayList<>();

    private Double totalPrice = 0.0;

 
    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

  

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Cart [cartId=" + cartId + ", totalPrice=" + totalPrice + "]";
        
    }
}
