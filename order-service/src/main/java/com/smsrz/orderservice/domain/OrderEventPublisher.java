package com.smsrz.orderservice.domain;

import com.smsrz.orderservice.ApplicationProperties;
import com.smsrz.orderservice.domain.Models.OrderCancelledEvent;
import com.smsrz.orderservice.domain.Models.OrderCreatedEvent;
import com.smsrz.orderservice.domain.Models.OrderDeliveredEvent;
import com.smsrz.orderservice.domain.Models.OrderErrorEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }
    private void send(String routingKey,Object payload){
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(),routingKey,payload);
    }
    public void publish(OrderCreatedEvent orderCreatedEvent) {
        this.send(properties.newOrdersQueue(),orderCreatedEvent);
    }
    public void publish(OrderDeliveredEvent orderDeliveredEvent) {
        this.send(properties.deliveredOrdersQueue(),orderDeliveredEvent);
    }
    public void publish(OrderErrorEvent errorEvent) {
        this.send(properties.errorOrdersQueue(),errorEvent);
    }
    public void publish(OrderCancelledEvent orderCancelledEvent) {
        this.send(properties.cancelledOrdersQueue(),orderCancelledEvent);
    }

}
