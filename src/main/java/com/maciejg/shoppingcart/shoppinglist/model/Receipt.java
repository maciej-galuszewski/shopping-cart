package com.maciejg.shoppingcart.shoppinglist.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Receipt {

    private final List<ReceiptRecord> records;

    private final BigDecimal totalAmount;
}
