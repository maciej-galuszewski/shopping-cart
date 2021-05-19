package com.maciejg.shoppingcart.producttype.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductTypeDto {

    private final String key;

    private final String name;

    private final BigDecimal price;

    private final AmountStrategy amountStrategy;
}
