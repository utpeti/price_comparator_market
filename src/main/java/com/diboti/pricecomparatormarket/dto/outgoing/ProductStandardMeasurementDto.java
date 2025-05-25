package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.Value;

@Value
public class ProductStandardMeasurementDto {
    String id;
    String name;
    String category;
    String brand;
    Double pricePerUnit;
    String unit;
    String currency;
}
