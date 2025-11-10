package com.scem.ecommerce.dto;

import com.scem.ecommerce.entity.Order;
import com.scem.ecommerce.entity.Payment;

public class OrderResponse {
    private Order order;
    private Payment payment;
    
	public OrderResponse(Order order, Payment payment) {
		super();
		this.order = order;
		this.payment = payment;
		
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
	
    
    
}
