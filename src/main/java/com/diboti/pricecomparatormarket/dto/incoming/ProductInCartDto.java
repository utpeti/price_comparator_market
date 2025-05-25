package com.diboti.pricecomparatormarket.dto.incoming;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ProductInCartDto {
    @NotNull
    String name;
}
