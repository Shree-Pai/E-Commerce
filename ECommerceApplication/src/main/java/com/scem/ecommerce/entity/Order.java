package com.scem.ecommerce.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scem.ecommerce.entity.enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<OrderItem> orderItems = new HashSet<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

   

    public Order() {}

    public Order(Long id, LocalDateTime orderDate, OrderStatus orderStatus, Double totalAmount,
                 User user, Set<OrderItem> orderItems, Payment payment) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.user = user;
        this.orderItems = orderItems;
        this.payment = payment;
    }

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus placed) {
        this.orderStatus = placed;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
