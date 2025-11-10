package com.scem.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scem.ecommerce.entity.enums.PaymentMethod;
import com.scem.ecommerce.entity.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	@OneToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order order;
	
	
	@JsonProperty("orderId")
	public Long getOrderId() {
	    return order != null ? order.getId() : null;
	}

	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private PaymentMethod paymentMethod;
	
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private PaymentStatus paymentStatus;
	
	
	@Column(unique = true)
	@NotBlank
	private String transactionId;
	

	private Double amountPaid;
	
	public Payment() {
		
	}

	public Payment(PaymentMethod paymentMethod, PaymentStatus paymentStatus, String transactionId, Double amountPaid) {
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.transactionId = transactionId;
		this.amountPaid = amountPaid;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", paymentMethod=" + paymentMethod + ", paymentStatus="
				+ paymentStatus + ", transactionId=" + transactionId + ", amountPaid=" + amountPaid + "]";
	}

}
