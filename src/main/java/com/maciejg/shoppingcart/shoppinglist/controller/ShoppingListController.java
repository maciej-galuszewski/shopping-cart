package com.maciejg.shoppingcart.shoppinglist.controller;

import com.maciejg.shoppingcart.shoppinglist.model.Order;
import com.maciejg.shoppingcart.shoppinglist.model.Receipt;
import com.maciejg.shoppingcart.shoppinglist.service.ReceiptStringConverter;
import com.maciejg.shoppingcart.shoppinglist.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    private final ReceiptStringConverter receiptStringConverter;

    @PostMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public Receipt placeOrderJson(@Valid @RequestBody Order order) {
        return shoppingListService.placeOrder(order);
    }

    @PostMapping(value = "/order", produces = MediaType.TEXT_PLAIN_VALUE)
    public String placeOrderPlainText(@Valid @RequestBody Order order) {
        return receiptStringConverter.transformToString(shoppingListService.placeOrder(order));
    }
}
