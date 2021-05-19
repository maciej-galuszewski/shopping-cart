package com.maciejg.shoppingcart.shoppinglist.service;

import com.maciejg.shoppingcart.amountcalculator.AmountCalculatorFactory;
import com.maciejg.shoppingcart.producttype.model.ProductTypeDto;
import com.maciejg.shoppingcart.producttype.service.ProductTypeService;
import com.maciejg.shoppingcart.shoppinglist.model.Order;
import com.maciejg.shoppingcart.shoppinglist.model.OrderRecord;
import com.maciejg.shoppingcart.shoppinglist.model.Receipt;
import com.maciejg.shoppingcart.shoppinglist.model.ReceiptRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ProductTypeService productTypeService;

    private final AmountCalculatorFactory amountCalculatorFactory;

    public Receipt placeOrder(Order order) {
        List<ReceiptRecord> receiptRecords = order.getOrderRecords().stream().map(this::calculateReceiptRecord).collect(Collectors.toList());
        BigDecimal totalAmount = receiptRecords.stream().map(ReceiptRecord::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return Receipt.builder()
                .records(receiptRecords)
                .totalAmount(totalAmount)
                .build();
    }

    private ReceiptRecord calculateReceiptRecord(OrderRecord orderRecord) {
        ProductTypeDto productTypeDto = productTypeService.getProductByKey(orderRecord.getProductKey());
        return ReceiptRecord.builder()
                .productKey(productTypeDto.getKey())
                .productName(productTypeDto.getName())
                .quantity(orderRecord.getQuantity())
                .price(productTypeDto.getPrice())
                .amount(amountCalculatorFactory.getAmountCalculator(productTypeDto.getAmountStrategy()).calculateAmount(orderRecord.getQuantity(), productTypeDto.getPrice()))
                .build();
    }
}
