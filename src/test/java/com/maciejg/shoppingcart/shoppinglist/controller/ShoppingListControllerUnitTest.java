package com.maciejg.shoppingcart.shoppinglist.controller;

import com.maciejg.shoppingcart.shoppinglist.model.Order;
import com.maciejg.shoppingcart.shoppinglist.model.OrderRecord;
import com.maciejg.shoppingcart.shoppinglist.model.Receipt;
import com.maciejg.shoppingcart.shoppinglist.service.ReceiptStringConverter;
import com.maciejg.shoppingcart.shoppinglist.service.ShoppingListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingListControllerUnitTest {

    @Mock
    private ShoppingListService shoppingListService;

    @Mock
    private ReceiptStringConverter receiptStringConverter;

    @InjectMocks
    private ShoppingListController sut;

    @Test
    void placeOrderJson_OrderProvided_ReturnReceiptFromService() {
        // given
        Order order = new Order(Collections.singletonList(new OrderRecord("key", 2)));
        Receipt receipt = Receipt.builder()
                .records(new ArrayList<>())
                .totalAmount(BigDecimal.ZERO)
                .build();
        when(shoppingListService.placeOrder(order)).thenReturn(receipt);

        // when
        Receipt result = sut.placeOrderJson(order);

        // then
        assertThat(result).isEqualTo(receipt);
    }

    @Test
    void placeOrderPlainText_OrderProvided_ReturnReceiptFromService() {
        // given
        Order order = new Order(Collections.singletonList(new OrderRecord("key", 2)));
        Receipt receipt = Receipt.builder()
                .records(new ArrayList<>())
                .totalAmount(BigDecimal.ZERO)
                .build();
        when(shoppingListService.placeOrder(order)).thenReturn(receipt);

        String testReceipt = "testReceipt";
        when(receiptStringConverter.transformToString(receipt)).thenReturn(testReceipt);

        // when
        String result = sut.placeOrderPlainText(order);

        // then
        assertThat(result).isEqualTo(testReceipt);
    }
}