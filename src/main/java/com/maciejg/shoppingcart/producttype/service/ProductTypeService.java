package com.maciejg.shoppingcart.producttype.service;

import com.maciejg.shoppingcart.producttype.exception.ProductTypeNotFoundException;
import com.maciejg.shoppingcart.producttype.model.ProductTypeDto;
import com.maciejg.shoppingcart.producttype.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    private final ProductTypeMapper productTypeMapper;

    public ProductTypeDto getProductByKey(String key) {
        return productTypeRepository.findByKey(key)
                .map(productTypeMapper::mapToDto)
                .orElseThrow(() -> new ProductTypeNotFoundException(key));
    }
}
