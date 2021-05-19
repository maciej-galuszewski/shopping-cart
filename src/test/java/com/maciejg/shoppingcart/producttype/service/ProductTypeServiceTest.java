package com.maciejg.shoppingcart.producttype.service;

import com.maciejg.shoppingcart.producttype.exception.ProductTypeNotFoundException;
import com.maciejg.shoppingcart.producttype.model.AmountStrategy;
import com.maciejg.shoppingcart.producttype.model.ProductTypeDto;
import com.maciejg.shoppingcart.producttype.model.ProductTypeEntity;
import com.maciejg.shoppingcart.producttype.repository.ProductTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductTypeServiceTest {

    @Mock
    private ProductTypeRepository productTypeRepository;

    @Mock
    private ProductTypeMapper productTypeMapper;

    @InjectMocks
    private ProductTypeService sut;

    @Test
    void getProductByKey_ProductEntityExists_ReturnProductDto() {
        // given
        String key = "key";
        String name = "name";
        BigDecimal price = new BigDecimal(3);
        AmountStrategy amountStrategy = AmountStrategy.THREE_FOR_TWO;

        ProductTypeEntity entity = new ProductTypeEntity(1L, key, name, price, amountStrategy);
        ProductTypeDto dto = ProductTypeDto.builder()
                .key(key)
                .name(name)
                .price(price)
                .amountStrategy(amountStrategy)
                .build();

        when(productTypeRepository.findByKey(key)).thenReturn(Optional.of(entity));
        when(productTypeMapper.mapToDto(entity)).thenReturn(dto);

        // when
        ProductTypeDto result = sut.getProductByKey(key);

        // then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getProductByKey_ProductEntityDoesNotExist_ThrowException() {
        // given
        String key = "key";
        when(productTypeRepository.findByKey(key)).thenReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> sut.getProductByKey(key));

        // then
        assertThat(thrown).isExactlyInstanceOf(ProductTypeNotFoundException.class);
        assertThat(thrown.getMessage()).isEqualTo("Product type not found: " + key);
        verifyNoInteractions(productTypeMapper);
    }
}