package com.scem.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<Object> handleEmptyCart(EmptyCartException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFound(OrderNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Object> handlePaymentNotFound(PaymentNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<Object> handleUnauthorizedAction(UnauthorizedActionException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        response.put("message", "Validation failed");
        response.put("details", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Catch-all for uncaught exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        return buildResponse("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponse("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
