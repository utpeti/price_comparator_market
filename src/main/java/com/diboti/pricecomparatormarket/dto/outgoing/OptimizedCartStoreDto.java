package com.diboti.pricecomparatormarket.dto.outgoing;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor(force = true)
public class OptimizedCartStoreDto {
    String store;
    Collection<String> productIds;
}
