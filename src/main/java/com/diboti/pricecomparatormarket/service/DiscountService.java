package com.diboti.pricecomparatormarket.service;

import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;

import java.util.Collection;

public interface DiscountService {
    Collection<Discount> getProductsWithHighestDiscounts() throws InvalidServiceOperationException;

    Collection<Discount> getTodayDiscounts() throws InvalidServiceOperationException;
}
