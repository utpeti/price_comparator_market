package com.diboti.pricecomparatormarket.service.impl;

import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.model.ProductAlert;
import com.diboti.pricecomparatormarket.repo.DiscountDao;
import com.diboti.pricecomparatormarket.repo.ProductAlertDao;
import com.diboti.pricecomparatormarket.repo.ProductDao;
import com.diboti.pricecomparatormarket.service.ProductService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductAlertDao productAlertDao;

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
    public ProductAlert existsProductAlert(Long id) {
        Optional<ProductAlert> productAlert = productAlertDao.findById(id);
        return productAlert.orElse(null);
    }

    @Override
    public void setProductAlert(String productId, String email, Double price) throws InvalidServiceOperationException {
        var productAlert = productAlertDao.findByProductIdAndEmail(productId, email);
        if(productAlert != null) {
            throw new InvalidServiceOperationException("Product with id: " + productId + " already has an alert with email: " + email);
        }

        var newProductAlert = new ProductAlert(productId, email, price);
        productAlertDao.save(newProductAlert);
    }

    @Override
    public void deleteProductAlert(Long id) throws InvalidServiceOperationException{
        try {
            productAlertDao.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Could not delete product alert with id: " + id);
        }
    }

    @Scheduled(fixedRate = 5, initialDelay = 5, timeUnit = TimeUnit.MINUTES)
    @Override
    public void checkProductPriceChange() {
        Collection<ProductAlert> productAlerts = productAlertDao.findAll();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        productAlerts.forEach(productAlert -> {
            var product = productDao.findById(productAlert.getProductId());
            var discounts = discountDao.findAllByProductId(productAlert.getProductId());
            var validDiscount = discounts.stream().filter(d -> {
                LocalDate fromDate = LocalDate.parse(d.getFrom_date(), formatter);
                LocalDate toDate = LocalDate.parse(d.getTo_date(), formatter);
                return (fromDate.isBefore(today) || fromDate.isEqual(today)) ||
                        (toDate.isAfter(today) || toDate.isEqual(today));
            }).sorted(Comparator.comparing(Discount::getPercentage_of_discount).reversed()).toList().getFirst();

            if(product.isPresent() && (((100 - validDiscount.getPercentage_of_discount()) * product.get().getPrice()
                    / 100 < productAlert.getPrice()) || product.get().getPrice() < productAlert.getPrice())) {
                System.out.println(product.get().getId() + ": send notification");
            }
        });
    }
}
