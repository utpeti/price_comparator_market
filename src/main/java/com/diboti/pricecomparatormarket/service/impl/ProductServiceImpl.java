package com.diboti.pricecomparatormarket.service.impl;

import com.diboti.pricecomparatormarket.dto.outgoing.ProductWithStoreInfo;
import com.diboti.pricecomparatormarket.model.Price;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.repo.PriceDao;
import com.diboti.pricecomparatormarket.repo.ProductDao;
import com.diboti.pricecomparatormarket.service.ProductService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PriceDao priceDao;

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
    public ProductWithStoreInfo getCheapestProductWithStore(String productName) throws InvalidServiceOperationException {
        try {
            Collection<Product> products = productDao.findAllByName(productName);
            if (products.isEmpty()) {
                throw new InvalidServiceOperationException("No products found with name: " + productName);
            }

            List<Price> allPrices = products.stream()
                    .map(product -> priceDao.findAllByProduct_Id(product.getId()))
                    .flatMap(Collection::stream)
                    .toList();

            Map<String, Price> latestPricesByProductAndStore =
                    allPrices.stream()
                            .collect(Collectors.groupingBy(
                                    price -> price.getProduct().getId() + "_" + price.getStore(),
                                    Collectors.collectingAndThen(
                                            Collectors.toList(),
                                            priceList -> priceList.stream()
                                                    .max(Comparator.comparing(Price::getDate))
                                                    .orElse(null)
                                    )
                            ))
                            .entrySet().stream()
                            .filter(entry -> entry.getValue() != null)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            Optional<Price> minPrice = latestPricesByProductAndStore.values().stream()
                    .min(Comparator.comparing(Price::getPrice));

            if (minPrice.isPresent()) {
                Price cheapestPrice = minPrice.get();
                Product cheapestProduct = cheapestPrice.getProduct();
                cheapestProduct.setPrice(cheapestPrice.getPrice());

                return new ProductWithStoreInfo(cheapestProduct, cheapestPrice.getStore());
            } else {
                throw new InvalidServiceOperationException("No prices found for products with name: " + productName);
            }
        } catch (Exception e) {
            throw new InvalidServiceOperationException("Failed to get cheapest product with name: " + productName, e);
        }
    }
}
