package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DiscountDetailDto {
    String id;
    String productId;
    String store;
    String fromDate;
    String toDate;
    String percentageOfDiscount;
    String addedOn;
}
