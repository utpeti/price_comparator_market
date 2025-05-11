package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
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
