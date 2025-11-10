package com.scem.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemId;
	
	@ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
	@JsonIgnore
	private Cart cart;
	
	@ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
	@JsonIgnore
	private Product product;
	
	private Integer quantity;
	
	private Double discount=0.0;
	
	private Double productPrice;
	
	
	public CartItem() {
		
	}


	public CartItem(Cart cart, Product product, Integer quantity, Double productPrice) {
	    this.cart = cart;
	    this.product = product;
	    this.quantity = quantity;
	    this.productPrice = productPrice;
	    this.discount = 0.0;  
	}



	public Long getCartItemId() {
		return cartItemId;
	}


	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}


	public Cart getCart() {
		return cart;
	}


	public void setCart(Cart cart) {
		this.cart = cart;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Double getDiscount() {
		return discount;
	}


	public void setDiscount(Double discount) {
		this.discount = discount;
	}


	public Double getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}


	@Override
	public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ", quantity=" + quantity + ", discount=" + discount
				+ ", productPrice=" + productPrice + "]";
	}
	
	
	
	

}
