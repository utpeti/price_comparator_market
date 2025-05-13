package com.diboti.pricecomparatormarket.controller;

import com.diboti.pricecomparatormarket.dto.incoming.ProductInCartDto;
import com.diboti.pricecomparatormarket.dto.outgoing.OptimizedShoppingCartItem;
import com.diboti.pricecomparatormarket.dto.outgoing.ProductWithStoreInfo;
import com.diboti.pricecomparatormarket.mapper.ProductMapper;
import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.service.ProductService;
import com.diboti.pricecomparatormarket.service.exceptions.InvalidServiceOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @PostMapping("/optimize")
    @ResponseStatus(HttpStatus.OK)
    public List<OptimizedShoppingCartItem> optimizeShoppingCart(@RequestBody List<ProductInCartDto> shoppingCart) {
        log.info("POST /api/v1/optimize called with shoppingCart: {}", shoppingCart);

        List<OptimizedShoppingCartItem> optimizedItems = new ArrayList<>();

        shoppingCart.forEach(productInCartDto -> {
            var currentProductName = productInCartDto.getName();
            try {
                ProductWithStoreInfo productWithStore = productService.getCheapestProductWithStore(currentProductName);
                Product cheapestProduct = productWithStore.getProduct();
                String store = productWithStore.getStore();

                OptimizedShoppingCartItem item = new OptimizedShoppingCartItem();
                item.setName(cheapestProduct.getName());
                item.setCategory(cheapestProduct.getCategory());
                item.setBrand(cheapestProduct.getBrand());
                item.setQuantity(cheapestProduct.getQuantity());
                item.setUnit(cheapestProduct.getUnit());
                item.setPrice(cheapestProduct.getPrice());
                item.setStore(store);

                optimizedItems.add(item);
            } catch (InvalidServiceOperationException e) {
                //throw new NotFoundException(Product.class, currentProductName);
            }
        });

        log.info("Optimized shopping cart: {}", optimizedItems);
        return optimizedItems;
    }
}
