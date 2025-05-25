package com.diboti.pricecomparatormarket.service.impl.utils;

import com.diboti.pricecomparatormarket.model.Discount;

public class DiscountUtils {
    public static double applyDiscount(double price, Discount discount) {
        if (discount == null) {
            return price;
        }
        return (100 - discount.getPercentageOfDiscount()) * price / 100;
    }
}
