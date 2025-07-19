package com.smsrz.orderservice.Web.Controller;


import com.smsrz.orderservice.Security.SecurityService;
import com.smsrz.orderservice.Web.Exception.OrderNotFoundException;
import com.smsrz.orderservice.domain.Models.CreateOrderRequest;
import com.smsrz.orderservice.domain.Models.CreateOrderResponse;
import com.smsrz.orderservice.domain.Models.OrderDTO;
import com.smsrz.orderservice.domain.Models.OrderSummary;
import com.smsrz.orderservice.domain.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name="security_auth")
public class OrderController {
    private final OrderService orderService;
    private final SecurityService securityService;

    public OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String userName = securityService.getLoginUserName();
        log.info("Creating Order for username : " + userName);
        return orderService.createOrder(userName,request);
    }

    @GetMapping
    List<OrderSummary> getOrders(){
        String userName = securityService.getLoginUserName();
        log.info("Getting Orders for username : " + userName);
        return orderService.findOrders(userName);
    }

    @GetMapping("/{orderNumber}")
    OrderDTO getOrder(@PathVariable(value = "orderNumber") String orderNumber){
        log.info("Getting Order for username : " + orderNumber);
        String userName = securityService.getLoginUserName();
        return orderService
                .findUserOrder(userName,orderNumber)
                .orElseThrow(()->new OrderNotFoundException(orderNumber));
    }
}

