package com.maciejg.shoppingcart.producttype.service;

import com.maciejg.shoppingcart.producttype.model.ProductTypeEntity;
import com.maciejg.shoppingcart.producttype.model.ProductTypeDto;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeMapper {

    public ProductTypeDto mapToDto(ProductTypeEntity productTypeEntity) {
        return ProductTypeDto.builder()
                .key(productTypeEntity.getKey())
                .name(productTypeEntity.getName())
                .price(productTypeEntity.getPrice())
                .amountStrategy(productTypeEntity.getAmountStrategy())
                .build();
    }
}
