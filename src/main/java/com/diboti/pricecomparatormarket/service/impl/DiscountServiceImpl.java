package com.diboti.pricecomparatormarket.service.impl;

import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.repo.DiscountDao;
import com.diboti.pricecomparatormarket.repo.ProductDao;
import com.diboti.pricecomparatormarket.service.DiscountService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private DiscountDao discountDao;

    @Override
    public Collection<Discount> getProductsWithHighestDiscounts() throws InvalidServiceOperationException {
        Collection<Discount> discounts;
        try {
            discounts = discountDao.findAll();
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Could not get discounts", e);
        }

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return discounts.stream().filter(d -> {
            LocalDate fromDate = LocalDate.parse(d.getFrom_date(), formatter);
            //LocalDate toDate = LocalDate.parse(d.getTo_date(), formatter);
            return (fromDate.isBefore(today) || fromDate.isEqual(today)); //&&
            //(toDate.isAfter(today) || toDate.isEqual(today));
        }).sorted(Comparator.comparing(Discount::getPercentage_of_discount).reversed()).toList();
    }

    @Override
    public Collection<Discount> getLatestDiscounts() throws InvalidServiceOperationException {
        Collection<Discount> discounts;
        try {
            discounts = discountDao.findAll();
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Could not get discounts", e);
        }

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return discounts.stream().filter(d -> {
                    LocalDate added_on = LocalDate.parse(d.getAdded_on(), formatter);
                    LocalDate yesterday = LocalDate.now().minusDays(1);
                    return !added_on.isBefore(yesterday);
                }).sorted(Comparator.comparing(Discount::getPercentage_of_discount).reversed())
                .toList();
    }

    @Override
    public Collection<Discount> getDiscountsByProductIdAndStore(String productId, String store) throws InvalidServiceOperationException {
        try {
            return discountDao.findAllByProductIdAndStore(productId, store);
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Could not get discounts", e);
        }
    }

    @Override
    public Collection<Discount> getDiscountsByProductIdAndProductCategory(String productId, String productCategory) throws InvalidServiceOperationException {
        try {
            return discountDao.findAllByProductIdAndProductCategory(productId, productCategory);
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Could not get discounts", e);
        }
    }

    @Override
    public Collection<Discount> getDiscountsByProductIdAndBrand(String productId, String brand) throws InvalidServiceOperationException {
        try {
            return discountDao.findAllByProductIdAndBrand(productId, brand);
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Could not get discounts", e);
        }
    }
}
