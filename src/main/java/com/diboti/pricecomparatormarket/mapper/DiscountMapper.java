package com.diboti.pricecomparatormarket.mapper;

import com.diboti.pricecomparatormarket.dto.outgoing.DiscountDetailDto;
import com.diboti.pricecomparatormarket.model.Discount;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
abstract public class DiscountMapper {
    @IterableMapping(elementTargetType = DiscountDetailDto.class)
    public abstract Collection<DiscountDetailDto> modelsToDetailDto(Iterable<Discount> discounts);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "product_id", source = "product.id")
    @Mapping(target = "store", source = "store")
    @Mapping(target = "from_date", source = "from_date")
    @Mapping(target = "to_date", source = "to_date")
    @Mapping(target = "percentage_of_discount", source = "percentage_of_discount")
    @Mapping(target = "added_on", source = "added_on")
    public abstract DiscountDetailDto modelToDetailDto(Discount model);
}
