package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
public class ProductDetailDto {
    Long id;
    String name;
    String category;
    String brand;
    Double quantity;
    String unit;
    Double price;
    String currency;
}
