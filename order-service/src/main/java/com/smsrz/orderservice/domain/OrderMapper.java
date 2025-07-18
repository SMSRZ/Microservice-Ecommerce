package com.smsrz.orderservice.domain;

import com.smsrz.orderservice.domain.Models.CreateOrderRequest;
import com.smsrz.orderservice.domain.Models.OrderDTO;
import com.smsrz.orderservice.domain.Models.OrderItem;
import com.smsrz.orderservice.domain.Models.OrderStatus;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

class OrderMapper {

    static OrderEntity convertToEntity(CreateOrderRequest request){
        OrderEntity newOrder = new OrderEntity();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setCustomer(request.customer());
        newOrder.setDeliveryAddress(request.deliveryAddress());
        Set<OrderItemEntity> orderItems = new HashSet<>();
        for (OrderItem item : request.items()) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setCode(item.code());
            orderItem.setName(item.name());
            orderItem.setPrice(item.price());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(newOrder);
            orderItems.add(orderItem);
        }
        newOrder.setItems(orderItems);
        return newOrder;
    }

    public static OrderDTO convertToDTO(OrderEntity orderEntity) {
        Set<OrderItem> items = orderEntity.getItems().stream().map(item -> new OrderItem(
                item.getCode()
                , item.getName()
                , item.getPrice()
                , item.getQuantity()))
                .collect(Collectors.toSet());
        return new OrderDTO(
                orderEntity.getOrderNumber(),
                orderEntity.getUserName(),
                items,
                orderEntity.getCustomer(),
                orderEntity.getDeliveryAddress(),
                orderEntity.getStatus(),
                orderEntity.getComments(),
                orderEntity.getCreatedAt()
        );
    }
    /*static OrderDTO convertToDTO(OrderEntity order) {
        Set<OrderItem> orderItems = order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderDTO(
                order.getOrderNumber(),
                order.getUserName(),
                orderItems,
                order.getCustomer(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getComments(),
                order.getCreatedAt());
    }*/
}
