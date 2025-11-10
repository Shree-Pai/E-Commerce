package com.scem.ecommerce.exception;

@SuppressWarnings("serial")
public class UnauthorizedActionException extends RuntimeException {
	public UnauthorizedActionException(String message) {
		super(message);
	}

}
