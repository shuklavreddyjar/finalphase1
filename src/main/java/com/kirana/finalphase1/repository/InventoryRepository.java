package com.kirana.finalphase1.repository;

import com.kirana.finalphase1.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * The interface Inventory repository.
 */
public interface InventoryRepository extends JpaRepository<InventoryEntity, UUID> {
}
