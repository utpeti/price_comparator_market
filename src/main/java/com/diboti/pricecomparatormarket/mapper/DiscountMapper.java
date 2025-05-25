package com.diboti.pricecomparatormarket.mapper;

import com.diboti.pricecomparatormarket.dto.outgoing.DiscountDetailDto;
import com.diboti.pricecomparatormarket.model.Discount;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class DiscountMapper {
    @IterableMapping(elementTargetType = DiscountDetailDto.class)
    public abstract Collection<DiscountDetailDto> modelsToDetailDto(Iterable<Discount> discounts);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "store", source = "store")
    @Mapping(target = "fromDate", source = "fromDate")
    @Mapping(target = "toDate", source = "toDate")
    @Mapping(target = "percentageOfDiscount", source = "percentageOfDiscount")
    @Mapping(target = "addedOn", source = "addedOn")
    public abstract DiscountDetailDto modelToDetailDto(Discount model);
}
