package com.maciejg.shoppingcart.amountcalculator;

import com.maciejg.shoppingcart.exception.UnsupportedAmountCalculatorException;
import com.maciejg.shoppingcart.producttype.model.AmountStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class AmountCalculatorFactory {

    private final Map<AmountStrategy, AmountCalculator> calculatorMap = new HashMap<>();

    public AmountCalculatorFactory(Set<AmountCalculator> amountCalculatorSet) {
        for (AmountCalculator amountCalculator : amountCalculatorSet) {
            calculatorMap.put(amountCalculator.getAmountStrategy(), amountCalculator);
        }
    }

    public AmountCalculator getAmountCalculator(AmountStrategy amountStrategy) {
        return Optional.ofNullable(calculatorMap.get(amountStrategy)).orElseThrow(() -> new UnsupportedAmountCalculatorException(amountStrategy));
    }
}
