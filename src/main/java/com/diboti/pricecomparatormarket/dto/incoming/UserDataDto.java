package com.diboti.pricecomparatormarket.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class UserDataDto {
    String email;
    Double price;
}
