package com.kirana.finalphase1.repository;

import com.kirana.finalphase1.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository
        extends JpaRepository<InventoryEntity, Long> {

    Optional<InventoryEntity> findByProductId(String productId);
}
