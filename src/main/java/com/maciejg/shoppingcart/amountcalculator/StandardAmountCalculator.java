package com.maciejg.shoppingcart.amountcalculator;

import com.maciejg.shoppingcart.producttype.model.AmountStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StandardAmountCalculator implements AmountCalculator {

    @Override
    public BigDecimal calculateAmount(int quantity, BigDecimal price) {
        return price.multiply(new BigDecimal(quantity));
    }

    @Override
    public AmountStrategy getAmountStrategy() {
        return AmountStrategy.STANDARD;
    }
}
