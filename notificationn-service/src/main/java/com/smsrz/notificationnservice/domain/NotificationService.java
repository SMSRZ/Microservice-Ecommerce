package com.smsrz.notificationnservice.domain;

import com.smsrz.notificationnservice.ApplicationProperties;
import com.smsrz.notificationnservice.domain.models.OrderCancelledEvent;
import com.smsrz.notificationnservice.domain.models.OrderCreatedEvent;
import com.smsrz.notificationnservice.domain.models.OrderDeliveredEvent;
import com.smsrz.notificationnservice.domain.models.OrderErrorEvent;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final JavaMailSender javaMailSender;
    private final ApplicationProperties properties;

    public NotificationService(JavaMailSender javaMailSender, ApplicationProperties properties) {
        this.javaMailSender = javaMailSender;
        this.properties = properties;
    }

    public void sendOrderCreatedNotification(OrderCreatedEvent orderCreatedEvent) {
        String message = """
                ===================================================
                                Order Created Notification
                                ----------------------------------------------------
                                Dear %s,
                                Your order with orderNumber: %s has been created successfully.
                
                                Thanks,
                                SMSRZ Team
                                ===================================================
                """.formatted(orderCreatedEvent.customer().name(),orderCreatedEvent.orderNumber());
        log.info("\n{}", message);
        sendEmail(orderCreatedEvent.customer().email(),"Order Created notification",message);
    }
    public void sendOrderDeliveredNotification(OrderDeliveredEvent event) {
        String message = """
                ===================================================
                                Order Delivered Notification
                                ----------------------------------------------------
                                Dear %s,
                                Your order with orderNumber: %s has been delivered successfully.
                
                                Thanks,
                                SMSRZ Team
                                ===================================================
                """.formatted(event.customer().name(),event.orderNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(),"Order delivered notification",message);
    }
    public void sendOrderCancelledNotification(OrderCancelledEvent event) {
        String message = """
                ===================================================
                                Order Delivered Notification
                                ----------------------------------------------------
                                Dear %s,
                                Your order with orderNumber: %s has been Cancelled due to location constraints.
                
                                Thanks,
                                SMSRZ Team
                                ===================================================
                """.formatted(event.customer().name(),event.orderNumber());
        log.info("\n{}", message);
        sendEmail(event.customer().email(),"Order Cancelled notification",message);
    }
    public void sendOrderErrorNotification(OrderErrorEvent event) {
        String message = """
                ===================================================
                                Order Delivered Notification
                                ----------------------------------------------------
                                Dear Team,
                                Your order with orderNumber: %s had some error.
                
                                Thanks,
                                SMSRZ Team
                                ===================================================
                """.formatted(event.customer().name(),event.orderNumber());
        log.info("\n{}", message);
        sendEmail(properties.supportEmail(), "Order Processing failure notification",message);
    }

    private void sendEmail(String recipient, String subject, String message) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");
            mimeMessageHelper.setFrom(properties.supportEmail());
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully to ", recipient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
