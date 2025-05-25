package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.*;

@Data
@NoArgsConstructor(force = true)
public class DiscountDetailDto {
    String id;
    String productId;
    String store;
    String fromDate;
    String toDate;
    String percentageOfDiscount;
    String addedOn;
}
