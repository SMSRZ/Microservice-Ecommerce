package com.smsrz.bookstorewebapp.web.controller;


import com.smsrz.bookstorewebapp.Clients.Orders.*;
import com.smsrz.bookstorewebapp.Services.SecurityHelper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@Controller
public class OrderController {
    private final OrderServiceClient orderServiceClient;
    private final SecurityHelper securityHelper;

    public OrderController(OrderServiceClient orderServiceClient, SecurityHelper securityHelper) {
        this.orderServiceClient = orderServiceClient;
        this.securityHelper = securityHelper;
    }

    @GetMapping("/cart")
    String cart() {
        return "cart";
    }

    @PostMapping("/api/orders")
    @ResponseBody
    OrderConfirmationDTO createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest){
        String accessToken = securityHelper.getAccessToken();
        Map<String,?> headers = Map.of("Authorization", "Bearer " + accessToken);
        return orderServiceClient.createOrder(headers,createOrderRequest);
    }
    @GetMapping("/orders/{orderNumber}")
    public String order(@PathVariable("orderNumber") String orderNumber, Model model) {
        model.addAttribute("orderNumber", orderNumber);
        return "order_details";
    }

    @GetMapping("/api/orders/{orderNumber}")
    @ResponseBody
    OrderDTO  getOrder(@PathVariable("orderNumber") String orderNumber) {
        String accessToken = securityHelper.getAccessToken();
        Map<String,?> headers = Map.of("Authorization", "Bearer " + accessToken);
        return orderServiceClient.getOrder(headers,orderNumber);
    }
    @GetMapping("/orders")
    String showOrders() {
        return "orders";
    }

    @GetMapping("/api/orders")
    @ResponseBody
    List<OrderSummary> getOrders() {

        String accessToken = securityHelper.getAccessToken();
        Map<String,?> headers = Map.of("Authorization", "Bearer " + accessToken);
        return orderServiceClient.getOrders(headers);
    }
 }
