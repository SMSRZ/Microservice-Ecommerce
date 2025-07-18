package com.smsrz.orderservice.domain.Exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
    public static OrderNotFoundException orderNotFoundException(String orderNumber) {
        return new OrderNotFoundException("Order with number "+orderNumber+" not found");
    }
}
