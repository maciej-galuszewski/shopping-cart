package com.maciejg.shoppingcart.shoppinglist.service;

import com.maciejg.shoppingcart.shoppinglist.model.Receipt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReceiptStringConverter {

    @Value("${product.name.display-maxlength}")
    private int PRODUCT_NAME_MAX_LENGTH;

    @Value("${product.quantity.display-maxlength}")
    private int QUANTITY_MAX_LENGTH;

    @Value("${product.price.display-maxlength}")
    private int PRICE_MAX_LENGTH;

    public String transformToString(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        sb.append("RECEIPT\n\n");

        sb.append(padValue("Product name", PRODUCT_NAME_MAX_LENGTH))
                .append(padValue("Qty", QUANTITY_MAX_LENGTH))
                .append(padValue("Price", PRICE_MAX_LENGTH))
                .append("Amount")
                .append("\n\n");
        receipt.getRecords().forEach(receiptRecord -> sb
                .append(padValue(receiptRecord.getProductName(), PRODUCT_NAME_MAX_LENGTH))
                .append(padValue(String.valueOf(receiptRecord.getQuantity()), QUANTITY_MAX_LENGTH))
                .append(padValue(receiptRecord.getPrice().toPlainString(), PRICE_MAX_LENGTH))
                .append(receiptRecord.getAmount().toPlainString())
                .append("\n"));

        sb.append("\n").append("Total sum: ").append(receipt.getTotalAmount().toPlainString());
        return sb.toString();
    }

    private String padValue(String str, int maxLength) {
        return String.format("%-" + (maxLength + 2) + "s", safeSubstring(str, maxLength));
    }

    private String safeSubstring(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }
}
