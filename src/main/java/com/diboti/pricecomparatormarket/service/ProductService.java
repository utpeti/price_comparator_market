package com.diboti.pricecomparatormarket.service;

import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;

public interface ProductService {
    Product existsProduct(long productId);

    Product getProduct(long productId) throws InvalidServiceOperationException;
}
