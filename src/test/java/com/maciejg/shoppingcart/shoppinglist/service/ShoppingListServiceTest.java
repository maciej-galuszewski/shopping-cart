package com.maciejg.shoppingcart.shoppinglist.service;

import com.maciejg.shoppingcart.amountcalculator.AmountCalculator;
import com.maciejg.shoppingcart.amountcalculator.AmountCalculatorFactory;
import com.maciejg.shoppingcart.producttype.model.AmountStrategy;
import com.maciejg.shoppingcart.producttype.model.ProductTypeDto;
import com.maciejg.shoppingcart.producttype.service.ProductTypeService;
import com.maciejg.shoppingcart.shoppinglist.model.Order;
import com.maciejg.shoppingcart.shoppinglist.model.OrderRecord;
import com.maciejg.shoppingcart.shoppinglist.model.Receipt;
import com.maciejg.shoppingcart.shoppinglist.model.ReceiptRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingListServiceTest {

    @Mock
    private ProductTypeService productTypeService;

    @Mock
    private AmountCalculatorFactory amountCalculatorFactory;

    @InjectMocks
    private ShoppingListService sut;

    @Test
    void placeOrder_OrderProvided_ReturnReceipt() {
        // given
        String key1 = "key1";
        String name1 = "name1";
        int quantity1 = 2;
        BigDecimal price1 = new BigDecimal(2);
        AmountStrategy amountStrategy1 = AmountStrategy.STANDARD;

        String key2 = "key2";
        String name2 = "name2";
        int quantity2 = 3;
        BigDecimal price2 = new BigDecimal(7);
        AmountStrategy amountStrategy2 = AmountStrategy.THREE_FOR_TWO;

        OrderRecord orderRecord1 = new OrderRecord(key1, quantity1);
        OrderRecord orderRecord2 = new OrderRecord(key2, quantity2);
        Order order = new Order(Arrays.asList(orderRecord1, orderRecord2));

        ProductTypeDto productTypeDto1 = ProductTypeDto.builder()
                .key(key1)
                .name(name1)
                .price(price1)
                .amountStrategy(amountStrategy1)
                .build();
        ProductTypeDto productTypeDto2 = ProductTypeDto.builder()
                .key(key2)
                .name(name2)
                .price(price2)
                .amountStrategy(amountStrategy2)
                .build();

        when(productTypeService.getProductByKey(key1)).thenReturn(productTypeDto1);
        when(productTypeService.getProductByKey(key2)).thenReturn(productTypeDto2);

        AmountCalculator amountCalculator1 = mock(AmountCalculator.class);
        AmountCalculator amountCalculator2 = mock(AmountCalculator.class);

        BigDecimal amount1 = new BigDecimal(1000);
        BigDecimal amount2 = new BigDecimal(2000);

        when(amountCalculator1.calculateAmount(quantity1, price1)).thenReturn(amount1);
        when(amountCalculator2.calculateAmount(quantity2, price2)).thenReturn(amount2);

        when(amountCalculatorFactory.getAmountCalculator(amountStrategy1)).thenReturn(amountCalculator1);
        when(amountCalculatorFactory.getAmountCalculator(amountStrategy2)).thenReturn(amountCalculator2);

        // when
        Receipt result = sut.placeOrder(order);

        // then
        assertThat(result.getRecords().size()).isEqualTo(2);
        assertThat(result.getRecords().get(0)).isEqualTo(ReceiptRecord.builder()
                .productKey(key1)
                .productName(name1)
                .quantity(quantity1)
                .price(price1)
                .amount(amount1)
                .build());
        assertThat(result.getRecords().get(1)).isEqualTo(ReceiptRecord.builder()
                .productKey(key2)
                .productName(name2)
                .quantity(quantity2)
                .price(price2)
                .amount(amount2)
                .build());
        assertThat(result.getTotalAmount()).isEqualTo(amount1.add(amount2));
    }
}