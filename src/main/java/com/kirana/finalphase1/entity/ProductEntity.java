package com.kirana.finalphase1.entity;

import com.kirana.finalphase1.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The type Product entity.
 */
@Entity
@Table(name = "products")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue
    @Column(name = "product_id", updatable = false, nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private CurrencyType currency;

    @Column(nullable = false)
    private boolean active = true;
}
