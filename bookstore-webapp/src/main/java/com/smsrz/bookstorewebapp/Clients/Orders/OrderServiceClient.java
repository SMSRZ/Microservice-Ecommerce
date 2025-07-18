package com.smsrz.bookstorewebapp.Clients.Orders;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Map;

public interface OrderServiceClient {

    @GetExchange("orders/api/orders")
    List<OrderSummary> getOrders();

    @PostExchange("orders/api/orders")
    OrderConfirmationDTO createOrder(@RequestBody CreateOrderRequest orderRequest);

    @GetExchange("/orders/api/orders/{orderNumber}")
    OrderDTO getOrder(@PathVariable String orderNumber);
}
