package com.smsrz.notificationnservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NotificationnServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationnServiceApplication.class, args);
    }

}
