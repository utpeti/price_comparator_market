package com.diboti.pricecomparatormarket.dto.incoming;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class ProductInCartDto {
    @NotNull
    @Size(min = 1, max = 100, message = "The length of the name of the product must be between 1 and 100 characters")
    String name;
}
