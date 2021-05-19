package com.maciejg.shoppingcart.producttype.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_type")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key", length = 50, unique = true, nullable = false)
    private String key;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @Column(name = "price", precision = 20, scale = 2, nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "amount_strategy", nullable = false)
    private AmountStrategy amountStrategy;
}
