package com.diboti.pricecomparatormarket.controller;

import com.diboti.pricecomparatormarket.controller.exceptions.NotFoundException;
import com.diboti.pricecomparatormarket.dto.outgoing.DiscountDetailDto;
import com.diboti.pricecomparatormarket.dto.outgoing.ProductDetailDto;
import com.diboti.pricecomparatormarket.mapper.DiscountMapper;
import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.service.DiscountService;
import com.diboti.pricecomparatormarket.service.ProductService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @Autowired
    private DiscountMapper discountMapper;

    @GetMapping("/best")
    @ResponseStatus(HttpStatus.OK)
    public Collection<DiscountDetailDto> getProductById() throws NotFoundException {
        log.info("GET /api/v1/discounts/best called");

        Collection<Discount> products;
        try {
            products = discountService.getProductsWithHighestDiscounts();
        } catch (InvalidServiceOperationException e) {
            throw new NotFoundException(Product.class, "");
        }

        return discountMapper.modelsToDetailDto(products);
    }
}
