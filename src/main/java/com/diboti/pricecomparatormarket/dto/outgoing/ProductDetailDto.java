package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ProductDetailDto {
    String id;
    String name;
    String category;
    String brand;
    Double quantity;
    String unit;
    Double price;
    String currency;
}
