package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DiscountDetailDto {
    String id;
    String product_id;
    String store;
    String from_date;
    String to_date;
    String percentage_of_discount;
    String added_on;
}
