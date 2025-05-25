package com.diboti.pricecomparatormarket.dto.incoming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserDataDto {
    @NotNull
    @Size(min = 1, max = 50)
    String email;

    @NotNull
    @Min(0)
    Double price;
}
