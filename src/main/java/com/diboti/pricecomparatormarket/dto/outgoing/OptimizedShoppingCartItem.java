package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.Data;

@Data
public class OptimizedShoppingCartItem {
    private String name;
    private String category;
    private String brand;
    private Double quantity;
    private String unit;
    private Double price;
    private String currency;
    private String store;
}