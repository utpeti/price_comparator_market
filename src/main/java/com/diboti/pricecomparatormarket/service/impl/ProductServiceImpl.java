package com.diboti.pricecomparatormarket.service.impl;

import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.repo.DiscountDao;
import com.diboti.pricecomparatormarket.repo.ProductDao;
import com.diboti.pricecomparatormarket.service.ProductService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product existsProduct(String productId) {
        Optional<Product> product = productDao.findById(productId);
        return product.orElse(null);
    }

    @Override
    public Product getProduct(String productId) throws InvalidServiceOperationException  {
        try {
            Optional<Product> product = productDao.findById(productId);
            if(product.isPresent()) {
                return product.get();
            } else {
                throw new InvalidServiceOperationException("Product with id: " + productId + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Failed to get product with id: " + productId, e);
        }
    }
}
