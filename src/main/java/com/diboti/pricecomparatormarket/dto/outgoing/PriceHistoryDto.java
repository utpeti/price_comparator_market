package com.diboti.pricecomparatormarket.dto.outgoing;

import com.diboti.pricecomparatormarket.model.Discount;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor(force = true)
public class PriceHistoryDto {
    Collection<Map<LocalDate, Double>> prices;
    Collection<Discount> discounts;
}
