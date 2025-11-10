package com.scem.ecommerce.service;

import com.scem.ecommerce.entity.Order;

import java.util.List;
public interface OrderService {

    // Admin
    List<Order> getAllOrders();
    Order getOrderByIdAdmin(Long id);
    Order updateOrderStatus(Long orderId, String status);

    // User
    Order placeOrderForCurrentUser();
    List<Order> getOrdersForCurrentUser();
    Order getOrderByIdForCurrentUser(Long id);
    void cancelOrderForCurrentUser(Long id);
}
