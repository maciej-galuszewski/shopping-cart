package com.maciejg.shoppingcart.amountcalculator;

import com.maciejg.shoppingcart.producttype.model.AmountStrategy;
import org.springframework.stereotype.Component;

@Component
public class ThreeForTwoDiscountCalculator extends QuantityDiscountCalculator {

    public ThreeForTwoDiscountCalculator() {
        super(3, 2);
    }

    @Override
    public AmountStrategy getAmountStrategy() {
        return AmountStrategy.THREE_FOR_TWO;
    }
}
