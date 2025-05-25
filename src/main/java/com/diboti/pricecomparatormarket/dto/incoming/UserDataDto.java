package com.diboti.pricecomparatormarket.dto.incoming;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserDataDto {
    @NotNull
    @Email(message = "Not a valid email address")
    @Size(min = 1, max = 50, message = "The length of the email must be between 1 and 50 characters")
    String email;

    @NotNull
    @Min(value = 0, message = "The price must be a positive number")
    Double price;
}
