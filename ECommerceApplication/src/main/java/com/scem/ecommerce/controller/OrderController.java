package com.scem.ecommerce.controller;

import com.scem.ecommerce.dto.OrderResponse;
import com.scem.ecommerce.dto.PaymentRequest;
import com.scem.ecommerce.entity.Order;
import com.scem.ecommerce.entity.Payment;
import com.scem.ecommerce.service.OrderService;
import com.scem.ecommerce.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;

    /** ADMIN Routes**/

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> getOrderByIdAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderByIdAdmin(id));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,
                                                   @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    /** USER Routes**/

    @PostMapping("/place-and-pay")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponse> placeOrderAndPay(@RequestBody PaymentRequest paymentRequest) {
        // Place order for current user
        Order order = orderService.placeOrderForCurrentUser();

        // Create payment and associate to order
        Payment payment = paymentService.createPaymentForOrder(
            order.getId(),
            paymentRequest.getMethod(),
            paymentRequest.getAmount(),
            paymentRequest.getTransactionId()
        );

        // It's now attached to order via paymentService logic
        OrderResponse response = new OrderResponse(order, payment);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Order>> getMyOrders() {
        return ResponseEntity.ok(orderService.getOrdersForCurrentUser());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrderForCurrentUser(id);
        return ResponseEntity.noContent().build();
    }
}
