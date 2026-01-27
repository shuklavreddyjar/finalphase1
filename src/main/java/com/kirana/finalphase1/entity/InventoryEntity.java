package com.kirana.finalphase1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inventory")
@Data
public class InventoryEntity {

    /**
     * Mongo Product _id stored as hex string
     */
    @Id
    @Column(name = "product_id", nullable = false, length = 24)
    private String productId;

    @Column(nullable = false)
    private Integer quantityAvailable;

    /**
     * Optimistic locking to avoid race conditions
     */
    @Version
    private Long version;
}
