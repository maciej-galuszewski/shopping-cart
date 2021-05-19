package com.maciejg.shoppingcart.amountcalculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@ParameterizedTest(name = "#{index} - {0} products with price of {1} should cost {2}")
@MethodSource("amountTestData")
public @interface AmountTest {
}
