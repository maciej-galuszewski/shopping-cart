package com.maciejg.shoppingcart.producttype.repository;


import com.maciejg.shoppingcart.producttype.model.ProductTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductTypeRepository extends CrudRepository<ProductTypeEntity, Long> {

    Optional<ProductTypeEntity> findByKey(String key);
}
