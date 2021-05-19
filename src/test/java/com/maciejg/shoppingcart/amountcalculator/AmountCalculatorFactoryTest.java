package com.maciejg.shoppingcart.amountcalculator;

import com.maciejg.shoppingcart.exception.UnsupportedAmountCalculatorException;
import com.maciejg.shoppingcart.producttype.model.AmountStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AmountCalculatorFactoryTest {

    private AmountCalculatorFactory sut;

    @Test
    void getAmountCalculator_ExistingCalculatorProvided_CalculatorReturned() {
        // given
        AmountCalculator amountCalculator = new StandardAmountCalculator();
        Set<AmountCalculator> amountCalculatorSet = Sets.newSet(amountCalculator);
        sut = new AmountCalculatorFactory(amountCalculatorSet);

        // when
        AmountCalculator result = sut.getAmountCalculator(amountCalculator.getAmountStrategy());

        // then
        assertThat(result).isEqualTo(amountCalculator);
    }

    @Test
    void getAmountCalculator_NonExistingCalculatorProvided_ExceptionThrown() {
        // given
        AmountCalculator amountCalculator = new StandardAmountCalculator();
        Set<AmountCalculator> amountCalculatorSet = Sets.newSet(amountCalculator);
        sut = new AmountCalculatorFactory(amountCalculatorSet);

        AmountStrategy testStrategy = AmountStrategy.THREE_FOR_TWO;

        // when
        Throwable thrown = catchThrowable(() -> sut.getAmountCalculator(testStrategy));

        // then
        assertThat(thrown).isExactlyInstanceOf(UnsupportedAmountCalculatorException.class);
        assertThat(thrown.getMessage()).isEqualTo("Unsupported amount calculator strategy: " + testStrategy);
    }
}