package com.maciejg.shoppingcart.producttype.service;

import com.maciejg.shoppingcart.producttype.model.AmountStrategy;
import com.maciejg.shoppingcart.producttype.model.ProductTypeDto;
import com.maciejg.shoppingcart.producttype.model.ProductTypeEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeMapperTest {

    private final ProductTypeMapper sut = new ProductTypeMapper();

    @Test
    void mapToDto_ProductEntityGiven_ProductDtoReturned() {
        // given
        Long id = 2L;
        String key = "key";
        String name = "name";
        BigDecimal price = new BigDecimal(4);
        AmountStrategy amountStrategy = AmountStrategy.THREE_FOR_TWO;
        ProductTypeEntity entity = new ProductTypeEntity(id, key, name, price, amountStrategy);

        // when
        ProductTypeDto result = sut.mapToDto(entity);

        // then
        assertThat(result.getKey()).isEqualTo(key);
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getPrice()).isEqualTo(price);
        assertThat(result.getAmountStrategy()).isEqualTo(amountStrategy);
    }
}