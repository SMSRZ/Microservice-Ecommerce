package com.smsrz.orderservice.Jobs;


import com.smsrz.orderservice.domain.OrderEventService;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component

public class OrderEventsPublishingJob {
    private final OrderEventService  orderEventService;

    public OrderEventsPublishingJob(OrderEventService orderEventService) {
        this.orderEventService = orderEventService;
    }

    @Scheduled(cron = "${orders.publish-order-events-job-cron}")
    @SchedulerLock(name = "publishOrderEvents")
    public void publishOrderEvents(){
        LockAssert.assertLocked();
        log.info("Publishing order events at {}", Instant.now());
        orderEventService.publishOrderEvents();
    }
}
