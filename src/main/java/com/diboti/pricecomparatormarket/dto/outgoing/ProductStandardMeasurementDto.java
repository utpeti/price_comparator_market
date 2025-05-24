package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProductStandardMeasurementDto {
    String id;
    String name;
    String category;
    String brand;
    Double pricePerUnit;
    String unit;
    String currency;
}
