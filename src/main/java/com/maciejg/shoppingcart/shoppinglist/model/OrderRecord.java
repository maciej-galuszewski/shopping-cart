package com.maciejg.shoppingcart.shoppinglist.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class OrderRecord {

    @NotEmpty
    private final String productKey;

    @Min(1)
    private final Integer quantity;
}
