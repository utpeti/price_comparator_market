package com.diboti.pricecomparatormarket.service.impl;

import com.diboti.pricecomparatormarket.dto.outgoing.ProductStandardMeasurementDto;
import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.model.ProductAlert;
import com.diboti.pricecomparatormarket.repo.DiscountDao;
import com.diboti.pricecomparatormarket.repo.ProductAlertDao;
import com.diboti.pricecomparatormarket.repo.ProductDao;
import com.diboti.pricecomparatormarket.repo.exceptions.InvalidDataAccessException;
import com.diboti.pricecomparatormarket.service.ProductService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import com.diboti.pricecomparatormarket.service.impl.utils.DiscountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    public Product getProduct(String productId) throws InvalidServiceOperationException {
        try {
            Optional<Product> product = productDao.findById(productId);
            if (product.isPresent()) {
                return product.get();
            } else {
                throw new InvalidServiceOperationException("Product with id: " + productId + " not found");
            }
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Failed to get product with id: " + productId, e);
        }
    }

    @Override
    public Collection<Map<LocalDate, Double>> getPricesByProductCategory(String productId, String productCategory)
            throws InvalidServiceOperationException {
        try {
            Collection<Object[]> rawData = productDao
                    .findAllPricesByProductIdAndProductCategory(productId, productCategory);
            Collection<Map<LocalDate, Double>> result = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Object[] row : rawData) {
                Double price = (Double) row[0];
                LocalDate date = LocalDate.parse((String) row[1], formatter);
                result.add(Map.of(date, price));
            }
            return result;
        } catch (InvalidDataAccessException e) {
            throw new InvalidServiceOperationException("Failed to get products with id: " + productId, e);
        }
    }

    @Override
    public Collection<Map<LocalDate, Double>> getPricesByBrand(String productId, String brand)
            throws InvalidServiceOperationException {
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
        } catch (InvalidDataAccessException e) {
            throw new InvalidServiceOperationException("Failed to get products with id: " + productId, e);
        }
    }

    @Override
    public Collection<Map<LocalDate, Double>> getPricesByStore(String productId, String store)
            throws InvalidServiceOperationException {
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
        } catch (InvalidDataAccessException e) {
            throw new InvalidServiceOperationException("Failed to get products with id: " + productId, e);
        }
    }

    @Override
    public ProductAlert existsProductAlert(Long id) {
        Optional<ProductAlert> productAlert = productAlertDao.findById(id);
        return productAlert.orElse(null);
    }

    @Override
    public void setProductAlert(String productId, String email, Double price)
            throws InvalidServiceOperationException {
        Object productAlert;
        try {
            productAlert = productAlertDao.findByProductIdAndEmail(productId, email);
        } catch (InvalidDataAccessException e) {
            throw new InvalidServiceOperationException(e.getMessage());
        }
        if (productAlert != null) {
            throw new InvalidServiceOperationException("Product with id: " + productId
                    + " already has an alert with email: " + email);
        }

        var newProductAlert = new ProductAlert(productId, email, price);
        productAlertDao.save(newProductAlert);
    }

    @Override
    public void deleteProductAlert(Long id) throws InvalidServiceOperationException {
        try {
            productAlertDao.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Could not delete product alert with id: " + id);
        }
    }

    @Scheduled(fixedRate = 5, initialDelay = 5, timeUnit = TimeUnit.MINUTES)
    @Override
    public void checkProductPriceChange() throws InvalidServiceOperationException {
        Collection<ProductAlert> productAlerts = productAlertDao.findAll();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (ProductAlert productAlert : productAlerts) {
            var product = productDao.findById(productAlert.getProductId());
            Collection<Discount> discounts;
            try {
                discounts = discountDao.findAllByProductId(productAlert.getProductId());
            } catch (InvalidDataAccessException e) {
                throw new InvalidServiceOperationException(e.getMessage());
            }
            var validDiscount = discounts.stream().filter(d -> {
                LocalDate fromDate = LocalDate.parse(d.getFromDate(), formatter);
                LocalDate toDate = LocalDate.parse(d.getToDate(), formatter);
                return fromDate.isBefore(today) || fromDate.isEqual(today)
                        || toDate.isAfter(today) || toDate.isEqual(today);
            }).sorted(Comparator.comparing(Discount::getPercentageOfDiscount).reversed()).toList().getFirst();

            if (product.isPresent()) {
                Collection<Map<LocalDate, Double>> prices = new ArrayList<>(List.of());
                try {
                    productDao.getPricesByProductId(product.get().getId()).forEach(row -> {
                        Double price1 = (Double) row[0];
                        LocalDate date = LocalDate.parse((String) row[1], formatter);

                        prices.add(Map.of(date, price1));
                    });
                } catch (InvalidDataAccessException e) {
                    throw new InvalidServiceOperationException(e.getMessage());
                }

                var price = prices.stream()
                        .max(Comparator.comparing(map -> map.keySet().iterator().next()))
                        .map(map -> map.values().iterator().next())
                        .orElseThrow();

                double discountedPrice = DiscountUtils.applyDiscount(price, validDiscount);

                if (discountedPrice < productAlert.getPrice() || price < productAlert.getPrice()) {
                    log.info(product.get().getId() + ": send notification");
                }
            }
        }
    }

    @Override
    public Collection<Product> getAlternativesById(String productId) throws InvalidServiceOperationException {
        var product = productDao.findById(productId);
        if (product.isEmpty()) {
            throw new InvalidServiceOperationException("Could not find product with id: " + productId);
        }

        try {
            return productDao
                    .findAllByName(product.get().getName()).stream()
                    .filter(product1 -> !Objects.equals(product1.getId(), productId)).collect(Collectors.toList());
        } catch (InvalidDataAccessException e) {
            throw new InvalidServiceOperationException(e.getMessage());
        }
    }

    @Override
    public Collection<ProductStandardMeasurementDto> calculateStandardMeasurement(Collection<Product> products)
            throws InvalidServiceOperationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            Collection<ProductStandardMeasurementDto> productStandardMeasurementDtos = new ArrayList<>(List.of());
            for (Product product : products) {
                if (Objects.equals(product.getUnit(), "ml")) {
                    product.setUnit("l");
                    product.setQuantity(product.getQuantity() / 1000);
                } else if (Objects.equals(product.getUnit(), "g")) {
                    product.setUnit("kg");
                    product.setQuantity(product.getQuantity() / 1000);
                }

                Collection<Map<LocalDate, Double>> prices = new ArrayList<>(List.of());
                try {
                    productDao.getPricesByProductId(product.getId()).forEach(row -> {
                        Double price1 = (Double) row[0];
                        LocalDate date = LocalDate.parse((String) row[1], formatter);

                        prices.add(Map.of(date, price1));
                    });
                } catch (InvalidDataAccessException e) {
                    throw new InvalidServiceOperationException(e.getMessage());
                }

                var price = prices.stream()
                        .max(Comparator.comparing(map -> map.keySet().iterator().next()))
                        .map(map -> map.values().iterator().next())
                        .orElseThrow();

                productStandardMeasurementDtos
                        .add(new ProductStandardMeasurementDto(product.getId(), product.getName(),
                                product.getCategory(), product.getBrand(), price / product.getQuantity(),
                                product.getUnit(), product.getCurrency()));
            }
            return productStandardMeasurementDtos;
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Failed to calculate standard measurement", e);
        }
    }

    @Override
    public String getWhereIsTheCheapest(String productId) throws InvalidServiceOperationException {
        try {
            Collection<Object[]> rawPrices = getRawPrices(productId);

            if (rawPrices.isEmpty()) {
                throw new InvalidServiceOperationException("No prices found for product: " + productId);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate today = LocalDate.now();

            Map<String, LocalDate> latestDateByStore = new ConcurrentHashMap<>();
            Map<String, Double> latestPriceByStore = getLatestPricesPerStore(rawPrices, formatter, latestDateByStore);

            String cheapestStore = null;
            double lowestPrice = Double.MAX_VALUE;

            for (Map.Entry<String, Double> storePrice : latestPriceByStore.entrySet()) {
                String store = storePrice.getKey();
                double latestPrice = storePrice.getValue();

                double finalPrice = getBestDiscountedPrice(productId, store, latestPrice, today, formatter);

                if (finalPrice < lowestPrice) {
                    lowestPrice = finalPrice;
                    cheapestStore = store;
                }
            }

            if (cheapestStore == null) {
                throw new InvalidServiceOperationException("Could not determine cheapest store for product: "
                        + productId);
            }

            return cheapestStore;
        } catch (IllegalArgumentException e) {
            throw new InvalidServiceOperationException("Failed to find cheapest store for product: " + productId, e);
        }
    }

    private Collection<Object[]> getRawPrices(String productId) throws InvalidServiceOperationException {
        try {
            return productDao.getPricesByProductId(productId);
        } catch (InvalidDataAccessException e) {
            throw new InvalidServiceOperationException("Failed to get prices for product: " + productId, e);
        }
    }

    private Map<String, Double> getLatestPricesPerStore(
            Collection<Object[]> rawPrices, DateTimeFormatter formatter, Map<String, LocalDate> latestDateByStore) {
        Map<String, Double> latestPriceByStore = new ConcurrentHashMap<>();

        for (Object[] row : rawPrices) {
            Double price = (Double) row[0];
            LocalDate date = LocalDate.parse((String) row[1], formatter);
            String store = (String) row[2];

            if (!latestDateByStore.containsKey(store) || date.isAfter(latestDateByStore.get(store))) {
                latestPriceByStore.put(store, price);
                latestDateByStore.put(store, date);
            }
        }

        return latestPriceByStore;
    }

    private Collection<Discount> getApplicableDiscounts(String productId, String store)
            throws InvalidServiceOperationException {
        try {
            return discountDao.findAllByProductIdAndStore(productId, store);
        } catch (InvalidDataAccessException e) {
            try {
                return discountDao.findAllByProductId(productId);
            } catch (InvalidDataAccessException ex) {
                throw new InvalidServiceOperationException("Failed to get discounts for product: " + productId, ex);
            }
        }
    }

    private double getBestDiscountedPrice(
            String productId, String store, double latestPrice, LocalDate today, DateTimeFormatter formatter)
            throws InvalidServiceOperationException {
        Collection<Discount> discounts = getApplicableDiscounts(productId, store);

        return discounts.stream()
                .filter(d -> {
                    LocalDate fromDate = LocalDate.parse(d.getFromDate(), formatter);
                    LocalDate toDate = LocalDate.parse(d.getToDate(), formatter);
                    return (fromDate.isBefore(today) || fromDate.isEqual(today))
                            && (toDate.isAfter(today) || toDate.isEqual(today));
                })
                .max(Comparator.comparing(Discount::getPercentageOfDiscount))
                .map(d -> DiscountUtils.applyDiscount(latestPrice, d))
                .orElse(latestPrice);
    }
}
