package com.smsrz.bookstorewebapp.Clients.Orders;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Customer(
        @NotBlank(message = "Name is required") String name ,
        @NotBlank(message = "Email is required") @Email String email,
        @NotBlank(message = "Phone is required")String phone
) {
}
