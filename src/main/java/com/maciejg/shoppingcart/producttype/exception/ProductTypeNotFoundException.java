package com.maciejg.shoppingcart.producttype.exception;

public class ProductTypeNotFoundException extends RuntimeException {

    public ProductTypeNotFoundException(String productType) {
        super("Product type not found: " + productType);
    }
}
