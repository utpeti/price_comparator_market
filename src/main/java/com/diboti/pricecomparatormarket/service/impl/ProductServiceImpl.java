package com.diboti.pricecomparatormarket.service.impl;

import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.repo.DiscountDao;
import com.diboti.pricecomparatormarket.repo.ProductDao;
import com.diboti.pricecomparatormarket.service.ProductService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private DiscountDao discountDao;

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

    @Override
    public Collection<Map<LocalDate, Double>> getPricesByProductCategory(String productId, String productCategory) throws InvalidServiceOperationException {
        try {
            Collection<Object[]> rawData = productDao.findAllPricesByProductIdAndProductCategory(productId, productCategory);
            Collection<Map<LocalDate, Double>> result = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Object[] row : rawData) {
                Double price = (Double) row[0];
                LocalDate date = LocalDate.parse((String) row[1], formatter);
                result.add(Map.of(date, price));
            }
            return result;
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Failed to get products with id: " + productId, e);
        }
    }

    @Override
    public Collection<Map<LocalDate, Double>> getPricesByBrand(String productId, String brand) throws InvalidServiceOperationException {
        try {
            Collection<Object[]> rawData = productDao.findAllPricesByProductIdAndBrand(productId, brand);
            Collection<Map<LocalDate, Double>> result = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Object[] row : rawData) {
                Double price = (Double) row[0];
                LocalDate date = LocalDate.parse((String) row[1], formatter);
                result.add(Map.of(date, price));
            }
            return result;
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Failed to get products with id: " + productId, e);
        }
    }

    @Override
    public Collection<Map<LocalDate, Double>> getPricesByStore(String productId, String store) throws InvalidServiceOperationException {
        try {
            Collection<Object[]> rawData = productDao.findAllPricesByProductIdAndStore(productId, store);
            Collection<Map<LocalDate, Double>> result = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Object[] row : rawData) {
                Double price = (Double) row[0];
                LocalDate date = LocalDate.parse((String) row[1], formatter);
                result.add(Map.of(date, price));
            }
            return result;
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Failed to get products with id: " + productId, e);
        }
    }

    @Override
    public void setPriceAlert(String productId) throws InvalidServiceOperationException {

    }
}
