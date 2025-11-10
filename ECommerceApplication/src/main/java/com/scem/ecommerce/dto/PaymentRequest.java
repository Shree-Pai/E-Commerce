package com.scem.ecommerce.dto;

import com.scem.ecommerce.entity.enums.PaymentMethod;

public class PaymentRequest {
    private PaymentMethod method;
    private Double amount;
    private String transactionId;
    
    
	public PaymentMethod getMethod() {
		return method;
	}
	public void setMethod(PaymentMethod method) {
		this.method = method;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

   
}
