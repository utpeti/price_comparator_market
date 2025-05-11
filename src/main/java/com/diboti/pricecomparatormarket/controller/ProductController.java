package com.diboti.pricecomparatormarket.controller;

import com.diboti.pricecomparatormarket.controller.exceptions.NotFoundException;
import com.diboti.pricecomparatormarket.dto.outgoing.ProductDetailDto;
import com.diboti.pricecomparatormarket.mapper.ProductMapper;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDetailDto getProductById(@PathVariable("id") String id) throws NotFoundException {
        log.info("GET /api/v1/product/{} called", id);

        var product = productService.existsProduct(id);
        if(product == null) {
            log.error("Product with id: {} not found", id);
            throw new NotFoundException(Product.class, id);
        }

        return productMapper.modelToDetailDto(product);
    }
}
