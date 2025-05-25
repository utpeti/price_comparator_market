package com.diboti.pricecomparatormarket.dto.outgoing;

import com.diboti.pricecomparatormarket.model.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PriceHistoryDto {
    Collection<Map<LocalDate, Double>> prices;
    Collection<Discount> discounts;
}
