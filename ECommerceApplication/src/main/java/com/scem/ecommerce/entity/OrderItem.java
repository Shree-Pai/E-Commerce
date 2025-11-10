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
@Table(name = "order_items")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long orderItemId;
	
	@ManyToOne
	@JoinColumn(name = "product_id",nullable = false)
	 private Product product;
	
	@ManyToOne
	@JoinColumn(name = "order_id",nullable = false)
	@JsonIgnore 
	 private Order order;
	
	 private Integer quantity;
	 private double discount;
	 private double orderedProductPrice; 
	 
	 public OrderItem() {
		
	}

	 public OrderItem(Product product, Order order, Integer quantity, double orderedProductPrice) {
		super();
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.orderedProductPrice = orderedProductPrice;
	 }

	 public Long getOrderItemId() {
		 return orderItemId;
	 }

	 public void setOrderItemId(Long orderItemId) {
		 this.orderItemId = orderItemId;
	 }

	 public Product getProduct() {
		 return product;
	 }

	 public void setProduct(Product product) {
		 this.product = product;
	 }

	 public Order getOrder() {
		 return order;
	 }

	 public void setOrder(Order order) {
		 this.order = order;
	 }

	 public Integer getQuantity() {
		 return quantity;
	 }

	 public void setQuantity(Integer quantity) {
		 this.quantity = quantity;
	 }

	 public double getDiscount() {
		 return discount;
	 }

	 public void setDiscount(double discount) {
		 this.discount = discount;
	 }

	 public double getOrderedProductPrice() {
		 return orderedProductPrice;
	 }

	 public void setOrderedProductPrice(double orderedProductPrice) {
		 this.orderedProductPrice = orderedProductPrice;
	 }

	 @Override
	 public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", quantity=" + quantity + ", orderedProductPrice="
				+ orderedProductPrice + "]";
	 } 

}
