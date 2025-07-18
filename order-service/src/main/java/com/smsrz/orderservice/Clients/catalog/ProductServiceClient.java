package com.smsrz.orderservice.Clients.catalog;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Slf4j
@Component
public class ProductServiceClient {

    private final RestClient restClient;

    public ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "catalog-service")
    @Retry(name = "catalog-service",fallbackMethod = "getProductByCodeFallback")
    public Optional<Product> getProductByCode(String code) {
        log.info("Fetching product by code {}", code);
        var product = restClient
                .get()
                .uri("/api/products/{code}", code)
                .retrieve()
                .body(Product.class);
        return Optional.ofNullable(product);
    }
    Optional<Product> getProductByCodeFallback(String code,Throwable throwable){
        System.out.println("ProductServiceClient.getProductByCodeFallback :code " +  code );
        //This is a business decision what to do if the logic still fails
        //in a real world application they dont want to lose the business so they either send an email to check whether the current status or
        return Optional.empty();
    }
}
