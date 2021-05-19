package com.maciejg.shoppingcart.amountcalculator;

import com.maciejg.shoppingcart.producttype.model.AmountStrategy;

import java.math.BigDecimal;

public interface AmountCalculator {

    BigDecimal calculateAmount(int quantity, BigDecimal price);

    AmountStrategy getAmountStrategy();
}
