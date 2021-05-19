package com.maciejg.shoppingcart.shoppinglist.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ReceiptRecord {

    private final String productKey;

    private final String productName;

    private final Integer quantity;

    private final BigDecimal price;

    private final BigDecimal amount;
}
