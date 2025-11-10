package com.scem.ecommerce.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.scem.ecommerce.entity.Order;
import com.scem.ecommerce.entity.Payment;
import com.scem.ecommerce.dao.OrderRepository;
import com.scem.ecommerce.dao.PaymentRepository;
import com.scem.ecommerce.entity.enums.PaymentMethod;
import com.scem.ecommerce.entity.enums.PaymentStatus;
import com.scem.ecommerce.service.impl.PaymentServiceImpl;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PaymentServiceTest {

    @Mock private PaymentRepository paymentRepo;
    @Mock private OrderRepository orderRepo;
    @InjectMocks private PaymentServiceImpl paymentService;

    private Order order;
    private Payment payment;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setId(1L);
        payment = new Payment();
        payment.setPaymentId(1L);
    }

    @Test
    void testCreatePaymentForOrder() {
        when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        when(paymentRepo.save(any(Payment.class))).thenAnswer(inv -> {
            Payment p = inv.getArgument(0);
            p.setPaymentId(10L);
            return p;
        });

        Payment result = paymentService.createPaymentForOrder(1L, PaymentMethod.CARD, 200.0, "TXN-1");
        assertEquals(PaymentMethod.CARD, result.getPaymentMethod());
        assertEquals(PaymentStatus.PENDING, result.getPaymentStatus());
        assertEquals(200.0, result.getAmountPaid());
    }

    @Test
    void testUpdatePaymentStatus() {
        payment.setPaymentStatus(PaymentStatus.PENDING);
        order.setPayment(payment);
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));
        when(paymentRepo.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepo.save(any(Order.class))).thenReturn(order);

        Payment updated = paymentService.updatePaymentStatus(1L, PaymentStatus.SUCCESS);
        assertEquals(PaymentStatus.SUCCESS, updated.getPaymentStatus());
    }

    @Test
    void testGetPayment() {
        when(paymentRepo.findById(1L)).thenReturn(Optional.of(payment));
        Payment p = paymentService.getPayment(1L);
        assertEquals(1L, p.getPaymentId());
    }
}
