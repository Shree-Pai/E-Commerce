package com.scem.ecommerce.exception;

@SuppressWarnings("serial")
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
