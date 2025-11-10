package com.scem.ecommerce.service.impl;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scem.ecommerce.dao.OrderRepository;
import com.scem.ecommerce.dao.PaymentRepository;
import com.scem.ecommerce.entity.Order;
import com.scem.ecommerce.entity.Payment;
import com.scem.ecommerce.entity.enums.OrderStatus;
import com.scem.ecommerce.entity.enums.PaymentStatus;
import com.scem.ecommerce.exception.OrderNotFoundException;
import com.scem.ecommerce.exception.PaymentNotFoundException;
import com.scem.ecommerce.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private OrderRepository orderRepo;

    public PaymentServiceImpl(PaymentRepository paymentRepo, OrderRepository orderRepo) {
        super();
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional
    @Override
    public Payment createPaymentForOrder(Long orderId, com.scem.ecommerce.entity.enums.PaymentMethod method, Double amount,
            String transactionId) {
        if (amount == null || amount <= 0)
            throw new IllegalArgumentException("Amount must be > 0");
        if (transactionId == null || transactionId.isBlank())
            throw new IllegalArgumentException("TransactionId required");

        Order order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order Not Found"));
        Payment payment = new Payment();
        payment.setPaymentMethod(method);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionId(transactionId);
        payment.setAmountPaid(amount);

        payment = paymentRepo.save(payment);
        order.setPayment(payment);
        orderRepo.save(order);

        logger.info("Created payment [{}] for order [{}] with amount: {}, method: {}, status: {}",
                payment.getPaymentId(), order.getId(), amount, method, PaymentStatus.PENDING);
        return payment;
    }

    @Override
    @Transactional
    public Payment updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment Not Found"));
        logger.info("Updating payment [{}] status from [{}] to [{}]", paymentId, payment.getPaymentStatus(), status);
        payment.setPaymentStatus(status);
        payment = paymentRepo.save(payment);

        Order order = payment.getOrder();
        if (order != null) {
            if (status == PaymentStatus.SUCCESS) {
                order.setOrderStatus(OrderStatus.PLACED);
            } else if (status == PaymentStatus.FAILED) {
                order.setOrderStatus(OrderStatus.FAILED);
            }
            orderRepo.save(order);
            logger.info("Order [{}] status updated to [{}] based on payment status [{}]", order.getId(),
                    order.getOrderStatus(), status);
        }

        return payment;
    }

    @Override
    public Payment getPayment(Long paymentId) {
        logger.debug("Fetching payment with id [{}]", paymentId);
        return paymentRepo.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Payment Not Found"));
    }

}
