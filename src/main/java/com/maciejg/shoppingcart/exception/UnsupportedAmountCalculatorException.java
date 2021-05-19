package com.maciejg.shoppingcart.exception;

import com.maciejg.shoppingcart.producttype.model.AmountStrategy;

public class UnsupportedAmountCalculatorException extends RuntimeException {

    public UnsupportedAmountCalculatorException(AmountStrategy amountStrategy) {
        super("Unsupported amount calculator strategy: " + amountStrategy);
    }
}
