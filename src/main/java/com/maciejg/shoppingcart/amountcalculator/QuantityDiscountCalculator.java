package com.maciejg.shoppingcart.amountcalculator;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public abstract class QuantityDiscountCalculator implements AmountCalculator {

    private final int originalQuantity;

    private final int discountQuantity;

    @Override
    public BigDecimal calculateAmount(int quantity, BigDecimal price) {
        int discountEligibleQuantity = quantity / originalQuantity;
        int regularEligibleQuantity = quantity % originalQuantity;

        BigDecimal discountAmount = price.multiply(new BigDecimal(discountEligibleQuantity)).multiply(new BigDecimal(discountQuantity));
        BigDecimal regularAmount = price.multiply(new BigDecimal(regularEligibleQuantity));
        return discountAmount.add(regularAmount);
    }
}
