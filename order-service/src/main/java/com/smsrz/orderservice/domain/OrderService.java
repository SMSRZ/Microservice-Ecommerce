package com.smsrz.orderservice.domain;


import com.smsrz.orderservice.domain.Models.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderValidator  orderValidator;
    private final OrderEventService orderEventService;
    private final static List<String> ALLOWED_COUNTRIES = List.of("INDIA","AMERICA","GERMANY","JAPAN");
    public OrderService(OrderRepository orderRepository,OrderValidator orderValidator,OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventService = orderEventService;
    }

    public CreateOrderResponse createOrder(String userName,CreateOrderRequest request) {
        orderValidator.validate(request);
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        newOrder.setUserName(userName);
        OrderEntity savedOrder = orderRepository.save(newOrder);
        log.info("Created Order for username : " + savedOrder.getUserName());
        //publish order event but there is a problem if a transaction is failed and beginning to roll back then
        //the event is already published and someone would consume it so in order to protect from this we have outbox pattern
        //
        OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(savedOrder);
        orderEventService.save(orderCreatedEvent);
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }

    public void processNewOrders() {
        List<OrderEntity> orders = orderRepository.findByStatus(OrderStatus.NEW);

        log.info("Found {} Orders", orders.size());
        for (OrderEntity order : orders) {
            this.process(order);
        }
    }
    private void process(OrderEntity order) {
        try{
            if(canBeDelivered(order)){
                log.info("OrderNumber :{} can be delivered", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(),OrderStatus.DELIVERED);
                orderEventService.save(OrderEventMapper.buildOrderDeliveredEvent(order));
            }else {
                log.info("OrderNumber :{} can not be delivered", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(),OrderStatus.CANCELLED);
                orderEventService.save(OrderEventMapper.buildOrderCancelledEvent(order,"Order cannot be delivered"));
            }
        } catch (RuntimeException e) {
            log.info("Failed to process order with number :{}" , order.getOrderNumber());
            orderRepository.updateOrderStatus(order.getOrderNumber(),OrderStatus.ERROR);
            orderEventService.save(OrderEventMapper.buildOrderErrorEvent(order,e.getMessage()));
        }
    }
    private boolean canBeDelivered(OrderEntity order) {
        return ALLOWED_COUNTRIES.contains(order.getDeliveryAddress().country().toUpperCase());
    }

    public List<OrderSummary> findOrders(String userName) {
        return orderRepository.findByUserName(userName);
    }

    public Optional<OrderDTO> findUserOrder(String userName,String orderNumber){
        return orderRepository.findByUserNameAndOrderNumber(userName,orderNumber).map(OrderMapper::convertToDTO);
    }
}
