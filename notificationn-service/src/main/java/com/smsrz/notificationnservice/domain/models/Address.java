package com.smsrz.notificationnservice.domain.models;

import jakarta.validation.constraints.NotBlank;

public record Address(
        @NotBlank(message = "Address Line 1 cannot be empty") String addressLine1,
        String addressLine2,
        @NotBlank(message = "City cannot be empty")String city,
        @NotBlank(message = "State cannot be empty")String state,
        @NotBlank(message = "Zipcode is required")String zipCode,
        @NotBlank(message = "Country is required")String country
) {
}
