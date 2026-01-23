package com.kirana.finalphase1.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * The type Inventory entity.
 */
@Entity
@Table(name = "inventory")
@Data
public class InventoryEntity {

    @Id
    @Column(name = "product_id", nullable = false)
    private java.util.UUID productId;

    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;
}
