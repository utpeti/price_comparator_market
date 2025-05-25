package com.diboti.pricecomparatormarket.service;

import com.diboti.pricecomparatormarket.dto.outgoing.ProductStandardMeasurementDto;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.model.ProductAlert;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public interface ProductService {
    Product existsProduct(String productId);

    Product getProduct(String productId) throws InvalidServiceOperationException;

    Collection<Map<LocalDate, Double>> getPricesByStore(String productId, String store)
            throws InvalidServiceOperationException;

    Collection<Map<LocalDate, Double>> getPricesByProductCategory(String productId, String productCategory)
            throws InvalidServiceOperationException;

    Collection<Map<LocalDate, Double>> getPricesByBrand(String productId, String brand)
            throws InvalidServiceOperationException;

    ProductAlert existsProductAlert(Long id);

    void setProductAlert(String productId, String email, Double price)
            throws InvalidServiceOperationException;

    void deleteProductAlert(Long id) throws InvalidServiceOperationException;

    void checkProductPriceChange();

    Collection<Product> getAlternativesById(String productId) throws InvalidServiceOperationException;

    Collection<ProductStandardMeasurementDto> calculateStandardMeasurement(Collection<Product> products)
            throws InvalidServiceOperationException;
}
