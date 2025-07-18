package com.smsrz.orderservice.Clients.catalog;

import java.math.BigDecimal;

public record Product(String code,
                      String name,
                      String description,
                      String imageUrl,
                      BigDecimal price) {
}
