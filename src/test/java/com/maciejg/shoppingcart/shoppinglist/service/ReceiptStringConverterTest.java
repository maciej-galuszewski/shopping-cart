package com.maciejg.shoppingcart.shoppinglist.service;

import com.maciejg.shoppingcart.shoppinglist.model.Receipt;
import com.maciejg.shoppingcart.shoppinglist.model.ReceiptRecord;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptStringConverterTest {

    private final ReceiptStringConverter sut = new ReceiptStringConverter();

    @Test
    void transformToString_ReceiptProvided_ReturnReceiptAsString() {
        // given
        ReflectionTestUtils.setField(sut, "PRODUCT_NAME_MAX_LENGTH", 15);
        ReflectionTestUtils.setField(sut, "QUANTITY_MAX_LENGTH", 15);
        ReflectionTestUtils.setField(sut, "PRICE_MAX_LENGTH", 15);

        Receipt receipt = Receipt.builder()
                .records(Arrays.asList(
                        ReceiptRecord.builder().productName("name").quantity(1).price(new BigDecimal("5")).amount(new BigDecimal("5")).build(),
                        ReceiptRecord.builder().productName("name2").quantity(4).price(new BigDecimal("5.21")).amount(new BigDecimal("20.84")).build()
                ))
                .totalAmount(new BigDecimal("25.84"))
                .build();

        String expectedResult = "RECEIPT\n" +
                "\n" +
                "Product name     Qty              Price            Amount\n" +
                "\n" +
                "name             1                5                5\n" +
                "name2            4                5.21             20.84\n" +
                "\n" +
                "Total sum: 25.84";

        // when
        String result = sut.transformToString(receipt);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}