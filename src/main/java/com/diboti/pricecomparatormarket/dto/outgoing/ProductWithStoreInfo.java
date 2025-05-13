package com.diboti.pricecomparatormarket.dto.outgoing;

import com.diboti.pricecomparatormarket.model.Product;
import lombok.Data;

@Data
public class ProductWithStoreInfo {
    private Product product;
    private String store;

    public ProductWithStoreInfo(Product productWithPrice, String store) {
        this.product = productWithPrice;
        this.store = store;
    }
}