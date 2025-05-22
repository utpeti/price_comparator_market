package com.diboti.pricecomparatormarket.service;

import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;

import java.util.Collection;

public interface DiscountService {
    Collection<Discount> getProductsWithHighestDiscounts() throws InvalidServiceOperationException;

    Collection<Discount> getLatestDiscounts() throws InvalidServiceOperationException;

    Collection<Discount> getDiscountsByProductIdAndStore(String productId, String store) throws InvalidServiceOperationException;

    Collection<Discount> getDiscountsByProductIdAndProductCategory(String productId, String productCategory) throws InvalidServiceOperationException;

    Collection<Discount> getDiscountsByProductIdAndBrand(String productId, String brand) throws InvalidServiceOperationException;
}
