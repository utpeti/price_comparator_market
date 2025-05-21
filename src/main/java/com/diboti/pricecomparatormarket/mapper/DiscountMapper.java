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
    public abstract DiscountDetailDto modelToDetailDto(Discount model);
}
