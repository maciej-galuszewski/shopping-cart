package com.maciejg.shoppingcart.amountcalculator;

import com.maciejg.shoppingcart.producttype.model.AmountStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StandardAmountCalculatorTest {

    private final StandardAmountCalculator sut = new StandardAmountCalculator();

    @Test
    void getAmountStrategy_MethodCalled_CorrectAmountStrategyReturned() {
        // given

        // when
        AmountStrategy result = sut.getAmountStrategy();

        // then
        assertThat(result).isEqualTo(AmountStrategy.STANDARD);
    }

    @AmountTest
    void calculateAmount_TestDataProvided_CorrectAmountReturned(ArgumentsAccessor arguments) {
        // given

        // when
        BigDecimal result = sut.calculateAmount(arguments.getInteger(0), arguments.get(1, BigDecimal.class));

        // then
        assertThat(result).isEqualTo(arguments.get(2, BigDecimal.class));
    }

    private static Stream<Arguments> amountTestData() {
        return Stream.<Arguments>builder()
                .add(Arguments.of(0, new BigDecimal(0), new BigDecimal(0)))
                .add(Arguments.of(0, new BigDecimal(1), new BigDecimal(0)))
                .add(Arguments.of(1, new BigDecimal(1), new BigDecimal(1)))
                .add(Arguments.of(2, new BigDecimal(1), new BigDecimal(2)))
                .add(Arguments.of(3, new BigDecimal(1), new BigDecimal(3)))
                .add(Arguments.of(4, new BigDecimal(1), new BigDecimal(4)))
                .add(Arguments.of(1, new BigDecimal(5), new BigDecimal(5)))
                .add(Arguments.of(2, new BigDecimal(5), new BigDecimal(10)))
                .add(Arguments.of(3, new BigDecimal(5), new BigDecimal(15)))
                .add(Arguments.of(4, new BigDecimal(5), new BigDecimal(20)))
                .build();
    }
}