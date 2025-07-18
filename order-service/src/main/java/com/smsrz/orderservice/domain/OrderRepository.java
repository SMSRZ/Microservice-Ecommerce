package com.smsrz.orderservice.domain;

import com.smsrz.orderservice.domain.Models.OrderStatus;
import com.smsrz.orderservice.domain.Models.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus orderStatus);

    default void updateOrderStatus(String orderNumber, OrderStatus orderStatus){
        OrderEntity order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(orderStatus);
        this.save(order);
    }

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    @Query("""
select new com.smsrz.orderservice.domain.Models.OrderSummary(o.orderNumber,o.status) 
from OrderEntity o 
where o.userName=:userName
""")
    List<OrderSummary> findByUserName(String userName);

    @Query("""
select distinct o
from OrderEntity o left join fetch o.items 
where o.userName=:userName and o.orderNumber=:orderNumber
""")
    Optional<OrderEntity> findByUserNameAndOrderNumber(String userName, String orderNumber);
}
