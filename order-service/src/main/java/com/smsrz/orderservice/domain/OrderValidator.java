package com.smsrz.orderservice.domain;

import com.smsrz.orderservice.Clients.catalog.Product;
import com.smsrz.orderservice.Clients.catalog.ProductServiceClient;
import com.smsrz.orderservice.domain.Exceptions.InvalidOrderException;
import com.smsrz.orderservice.domain.Models.CreateOrderRequest;
import com.smsrz.orderservice.domain.Models.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class OrderValidator {
    private final ProductServiceClient client;

    public OrderValidator(ProductServiceClient client) {
        this.client = client;
    }

    void validate(CreateOrderRequest request) {
        Set<OrderItem> items = request.items();
        for(OrderItem item : items) {
            Product product =  client.getProductByCode(item.code()).orElseThrow(()->new InvalidOrderException(
                    "Invalid Product code : "+ item.code()
            ));
            if (item.price().compareTo(product.price())!=0){
                log.error("product price not matching . Actual price:{} , Received Price :{}",
                        item.price(), product.price());
                throw new InvalidOrderException("Product price not matching");
            }
        }
    }
}
