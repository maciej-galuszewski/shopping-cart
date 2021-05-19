package com.maciejg.shoppingcart.shoppinglist.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class Order {

    @Valid
    @NotEmpty(message = "Order cannot be empty")
    private final List<OrderRecord> orderRecords;
}
