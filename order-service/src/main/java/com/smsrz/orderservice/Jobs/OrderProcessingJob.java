package com.smsrz.orderservice.Jobs;

import com.smsrz.orderservice.domain.OrderService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class OrderProcessingJob {
    private final OrderService orderService;

    public OrderProcessingJob(OrderService orderService) {
        this.orderService = orderService;
    }


    @Scheduled(cron = "${orders.process-new-orders-job-cron}")
    @SchedulerLock(name = "processNewOrders")
    public void processNewOrders(){
        LockAssert.assertLocked();
        log.info("Processing order events at {}", Instant.now());
        orderService.processNewOrders();
    }
}
