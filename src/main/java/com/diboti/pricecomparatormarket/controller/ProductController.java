package com.diboti.pricecomparatormarket.controller;

import com.diboti.pricecomparatormarket.controller.exceptions.InvalidFilterException;
import com.diboti.pricecomparatormarket.controller.exceptions.NotFoundException;
import com.diboti.pricecomparatormarket.controller.exceptions.ServerErrorException;
import com.diboti.pricecomparatormarket.dto.incoming.ProductInCartDto;
import com.diboti.pricecomparatormarket.dto.incoming.UserDataDto;
import com.diboti.pricecomparatormarket.dto.outgoing.OptimizedCartStoreDto;
import com.diboti.pricecomparatormarket.dto.outgoing.PriceHistoryDto;
import com.diboti.pricecomparatormarket.dto.outgoing.ProductStandardMeasurementDto;
import com.diboti.pricecomparatormarket.mapper.ProductMapper;
import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.model.ProductAlert;
import com.diboti.pricecomparatormarket.service.DiscountService;
import com.diboti.pricecomparatormarket.service.ProductService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private DiscountService discountService;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/{id}/alternatives")
    @ResponseStatus(HttpStatus.OK)
    public Collection<ProductStandardMeasurementDto> getAlternatives(@PathVariable("id") String id)
            throws InvalidServiceOperationException, NotFoundException {
        log.info("GET /api/v1/product/{}/alternatives called", id);

        var product = productService.existsProduct(id);
        if (product == null) {
            log.error("Product with id: {} not found", id);
            throw new NotFoundException(Product.class, id);
        }

        var alternativeProducts = productService.getAlternativesById(id);

        return productService.calculateStandardMeasurement(alternativeProducts);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceHistoryDto getProductById(@PathVariable("id") String id, @RequestParam Map<String, String> filters)
            throws NotFoundException, ServerErrorException, InvalidFilterException {
        log.info("GET /api/v1/product/{} called", id);

        var product = productService.existsProduct(id);
        if (product == null) {
            log.error("Product with id: {} not found", id);
            throw new NotFoundException(Product.class, id);
        }

        try {
            Collection<Map<LocalDate, Double>> prices;
            Collection<Discount> discounts;
            if (filters.containsKey("store")) {
                prices = productService.getPricesByStore(id, filters.get("store"));
                discounts = discountService.getDiscountsByProductIdAndStore(id, filters.get("store"));
            } else if (filters.containsKey("product_category")) {
                prices = productService.getPricesByProductCategory(id, filters.get("product_category"));
                discounts = discountService
                        .getDiscountsByProductIdAndProductCategory(id, filters.get("product_category"));
            } else if (filters.containsKey("brand")) {
                prices = productService.getPricesByBrand(id, filters.get("brand"));
                discounts = discountService.getDiscountsByProductIdAndBrand(id, filters.get("brand"));
            } else {
                throw new InvalidFilterException("Invalid filter(s) provided");
            }

            PriceHistoryDto priceHistoryDto = new PriceHistoryDto();
            priceHistoryDto.setPrices(prices);
            priceHistoryDto.setDiscounts(discounts);

            return priceHistoryDto;
        } catch (InvalidServiceOperationException e) {
            throw new ServerErrorException(e);
        }
    }

    @PostMapping("notify/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void alertOnProductPrice(@PathVariable("id") String id, @Valid @RequestBody UserDataDto userData)
            throws ServerErrorException, NotFoundException {
        log.info("GET /api/v1/product/notify/{} called", id);

        var product = productService.existsProduct(id);
        if (product == null) {
            log.error("Product with id: {} not found", id);
            throw new NotFoundException(Product.class, id);
        }

        try {
            productService.setProductAlert(id, userData.getEmail(), userData.getPrice());
        } catch (InvalidServiceOperationException e) {
            throw new ServerErrorException(e);
        }
    }

    @DeleteMapping("notify/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlertOnProductPrice(@PathVariable("id") Long id, @Valid @RequestBody UserDataDto userData)
            throws ServerErrorException, NotFoundException {
        log.info("DELETE /api/v1/product/notify/{} called", id);

        log.info("User: {}", userData); //check if user data valid and exists in db

        var productAlert = productService.existsProductAlert(id);
        if (productAlert == null) {
            log.error("Product alert with id: {} not found", id);
            throw new NotFoundException(ProductAlert.class, id.toString());
        }

        try {
            productService.deleteProductAlert(id);
        } catch (InvalidServiceOperationException e) {
            throw new ServerErrorException(e);
        }
    }

    @PostMapping("/optimize")
    @ResponseStatus(HttpStatus.OK)
    public List<OptimizedCartStoreDto> optimizeShoppingCart(@Valid @RequestBody List<ProductInCartDto> shoppingCart)
            throws NotFoundException, ServerErrorException {
        log.info("POST /api/v1/optimize called with shoppingCart: {}", shoppingCart);

        Map<String, List<String>> storeProductsMap = new ConcurrentHashMap<>();

        for (ProductInCartDto productInCartDto : shoppingCart) {
            var id = productInCartDto.getId();
            var product = productService.existsProduct(id);
            if (product == null) {
                log.error("Product with id: {} not found", id);
                throw new NotFoundException(ProductAlert.class, id);
            }

            try {
                var store = productService.getWhereIsTheCheapest(id);
                storeProductsMap.computeIfAbsent(store, k -> new ArrayList<>()).add(id);
            } catch (InvalidServiceOperationException e) {
                throw new ServerErrorException(e);
            }
        }

        List<OptimizedCartStoreDto> optimizedItems = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : storeProductsMap.entrySet()) {
            OptimizedCartStoreDto storeDto = new OptimizedCartStoreDto();
            storeDto.setStore(entry.getKey());
            storeDto.setProductIds(entry.getValue());
            optimizedItems.add(storeDto);
        }

        log.info("Optimized shopping cart: {}", optimizedItems);
        return optimizedItems;
    }
}
