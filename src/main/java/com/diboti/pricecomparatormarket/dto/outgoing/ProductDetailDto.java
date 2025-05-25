package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
