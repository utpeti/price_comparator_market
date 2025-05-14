package com.diboti.pricecomparatormarket.service;

import com.diboti.pricecomparatormarket.dto.outgoing.ProductWithStoreInfo;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;

public interface ProductService {
    Product existsProduct(String productId);

    Product getProduct(String productId) throws InvalidServiceOperationException;

    ProductWithStoreInfo getCheapestProductWithStore(String productName) throws InvalidServiceOperationException;
}
