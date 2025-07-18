package com.smsrz.notificationnservice.events;


import com.smsrz.notificationnservice.NotificationnServiceApplication;
import com.smsrz.notificationnservice.domain.NotificationService;
import com.smsrz.notificationnservice.domain.OrderEventEntity;
import com.smsrz.notificationnservice.domain.OrderEventRepository;
import com.smsrz.notificationnservice.domain.models.OrderCancelledEvent;
import com.smsrz.notificationnservice.domain.models.OrderCreatedEvent;
import com.smsrz.notificationnservice.domain.models.OrderDeliveredEvent;
import com.smsrz.notificationnservice.domain.models.OrderErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);
    private final NotificationService  notificationService;
    private final OrderEventRepository  orderEventRepository;

    public OrderEventHandler(NotificationService notificationService, OrderEventRepository orderEventRepository) {
        this.notificationService = notificationService;
        this.orderEventRepository = orderEventRepository;
    }

    //here we have used "spel" -> spring expression language
    @RabbitListener(queues = "${notification.new-orders-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        log.info("Received OrderCreatedEvent {}", orderCreatedEvent);
        if(orderEventRepository.existsByEventId(orderCreatedEvent.eventId())){
            log.warn("Received duplicate created order event : {}", orderCreatedEvent.eventId());
            return;
        }
        notificationService.sendOrderCreatedNotification(orderCreatedEvent);
        OrderEventEntity orderEventEntity = new OrderEventEntity(orderCreatedEvent.eventId());
        orderEventRepository.save(orderEventEntity);
    }
    @RabbitListener(queues = "${notification.cancelled-orders-queue}")
    void handleOrderCancelledEvent(OrderCancelledEvent event) {
        log.info("Received OrderCancelledEvent {}", event);
        if(orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate Cancelled order event : {}", event.eventId());
            return;
        }
        notificationService.sendOrderCancelledNotification(event);
        OrderEventEntity orderEventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEventEntity);
    }
    @RabbitListener(queues = "${notification.error-orders-queue}")
    void handleOrderErrorEvent(OrderErrorEvent event) {
        log.info("Received OrderErrorEvent {}", event);
        if(orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate Error order event : {}", event.eventId());
            return;
        }
        notificationService.sendOrderErrorNotification(event);
        OrderEventEntity orderEventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEventEntity);
    }
    @RabbitListener(queues = "${notification.delivered-orders-queue}")
    void handleOrderDeliveredEvent(OrderDeliveredEvent event) {
        log.info("Received OrderDeliveredEvent {}", event);
        if(orderEventRepository.existsByEventId(event.eventId())){
            log.warn("Received duplicate delivered order event : {}", event.eventId());
            return;
        }
        notificationService.sendOrderDeliveredNotification(event);
        OrderEventEntity orderEventEntity = new OrderEventEntity(event.eventId());
        orderEventRepository.save(orderEventEntity);
    }
}
