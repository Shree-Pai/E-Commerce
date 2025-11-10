package com.scem.ecommerce.service;

import com.scem.ecommerce.entity.Payment;
import com.scem.ecommerce.entity.enums.PaymentMethod;
import com.scem.ecommerce.entity.enums.PaymentStatus;

public interface PaymentService {
	
	Payment createPaymentForOrder(Long orderId, PaymentMethod method, Double amount, String transactionId);
    Payment updatePaymentStatus(Long paymentId, PaymentStatus status);
    Payment getPayment(Long paymentId);

}
