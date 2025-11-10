package com.scem.ecommerce.exception;

@SuppressWarnings("serial")
public class EmptyCartException extends RuntimeException {
    public EmptyCartException(String message) {
        super(message);
    }
}
