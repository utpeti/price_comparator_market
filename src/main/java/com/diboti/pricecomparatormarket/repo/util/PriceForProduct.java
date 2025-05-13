package com.diboti.pricecomparatormarket.repo.util;

import lombok.Data;

@Data
public class PriceForProduct {
    private String store;
    private String brand;
    private Double price;
    private String date;
}
