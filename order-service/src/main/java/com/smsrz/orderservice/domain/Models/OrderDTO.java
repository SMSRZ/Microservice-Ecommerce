package com.smsrz.orderservice.domain.Models;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(String orderNumber,
                       String user,
                       Set<OrderItem> items,
                       Customer customer,
                       Address deliveryAddress,
                       OrderStatus status,
                       String comments,
                       LocalDateTime createdAt) {
}
