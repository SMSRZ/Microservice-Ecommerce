package com.smsrz.catalogservice.domain;

public class ProductNotFoundException extends RuntimeException{
    ProductNotFoundException(String message){
        super(message);
    }
    public static ProductNotFoundException forCode(String code){
    return new ProductNotFoundException("Product not found with code " + code);
    }
}
