package com.scem.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scem.ecommerce.entity.Payment;
import com.scem.ecommerce.entity.enums.PaymentMethod;
import com.scem.ecommerce.entity.enums.PaymentStatus;
import com.scem.ecommerce.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {

		this.paymentService = paymentService;
	}
	
	 /** USER Routes**/
	@GetMapping("/{paymentId}")
	    public ResponseEntity<Payment> getPayment(@PathVariable Long paymentId) {
	        return ResponseEntity.ok(paymentService.getPayment(paymentId));
	    }
	
	 
	@PostMapping("/order/{orderId}")
    public ResponseEntity<Payment> createPayment(@PathVariable Long orderId, @RequestParam PaymentMethod method,
    		@RequestParam Double amount,@RequestParam String transactionId) {
		
        return ResponseEntity.ok(paymentService.createPaymentForOrder(orderId, method, amount, transactionId));
    }

	@PutMapping("/{paymentId}/status")
	public ResponseEntity<Payment> updateStatus(@PathVariable Long paymentId, @RequestParam PaymentStatus status) {
	    return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
	}

   
}