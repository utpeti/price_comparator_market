package com.diboti.pricecomparatormarket.controller;

import com.diboti.pricecomparatormarket.controller.exceptions.NotFoundException;
import com.diboti.pricecomparatormarket.controller.exceptions.ServerErrorException;
import com.diboti.pricecomparatormarket.dto.incoming.UserDataDto;
import com.diboti.pricecomparatormarket.dto.outgoing.PriceHistoryDto;
import com.diboti.pricecomparatormarket.mapper.ProductMapper;
import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.service.DiscountService;
import com.diboti.pricecomparatormarket.service.ProductService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;
    @Autowired
    private DiscountService discountService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceHistoryDto getProductById(@PathVariable("id") String id, @RequestParam Map<String, String> filters) throws NotFoundException, ServerErrorException {
        log.info("GET /api/v1/product/{} called", id);

        var product = productService.existsProduct(id);
        if (product == null) {
            log.error("Product with id: {} not found", id);
            throw new NotFoundException(Product.class, id);
        }

        try {
            Collection<Map<LocalDate, Double>> prices = new ArrayList<>();
            Collection<Discount> discounts = new ArrayList<>();
            if(filters.containsKey("store")) {
                prices = productService.getPricesByStore(id, filters.get("store"));
                discounts = discountService.getDiscountsByProductIdAndStore(id, filters.get("store"));
            } else if(filters.containsKey("product_category")) {
                prices = productService.                                                                                        getPricesByProductCategory(id, filters.get("product_category"));
                discounts = discountService.getDiscountsByProductIdAndProductCategory(id, filters.get("product_category"));
            } else if(filters.containsKey("brand")) {
                prices = productService.getPricesByBrand(id, filters.get("brand"));
                discounts = discountService.getDiscountsByProductIdAndBrand(id, filters.get("brand"));
            } else {
                throw new ServerErrorException(new Exception("Invalid filter parameter"));
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
    void alertOnProductPrice(@PathVariable("id") String id, @RequestBody UserDataDto userData) throws ServerErrorException, NotFoundException {
        log.info("GET /api/v1/product/notify/{} called", id);

        if (userData == null) {
            throw new ServerErrorException(new Exception("Invalid user data"));
        } else if (userData.getEmail() == null) {
            throw new ServerErrorException(new Exception("Invalid user data"));
        }

        var product = productService.existsProduct(id);
        if (product == null) {
            log.error("Product with id: {} not found", id);
            throw new NotFoundException(Product.class, id);
        }

        try {
            productService.setProductAlert(id, userData.getEmail());
        } catch (InvalidServiceOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("notify/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlertOnProductPrice(@PathVariable("id") String id, @RequestBody UserDataDto userData) throws ServerErrorException, NotFoundException {
        log.info("DELETE /api/v1/product/notify/{} called", id);

        if (userData == null) {
            throw new ServerErrorException(new Exception("Invalid user data"));
        } else if (userData.getEmail() == null) {
            throw new ServerErrorException(new Exception("Invalid user data"));
        }

        try {
            productService.deleteProductAlert(id);
        } catch (InvalidServiceOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
