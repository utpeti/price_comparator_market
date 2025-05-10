package com.diboti.pricecomparatormarket.mapper;

import com.diboti.pricecomparatormarket.dto.outgoing.ProductDetailDto;
import com.diboti.pricecomparatormarket.model.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring",
unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ProductMapper {

    @IterableMapping(elementTargetType = ProductDetailDto.class)
    public abstract Collection<ProductDetailDto> modelsToDetailDto(Iterable<Product> products);

    public abstract ProductDetailDto modelToDetailDto(Product model);
}
