package com.kirana.finalphase1.repository;

import com.kirana.finalphase1.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * The interface Product repository.
 */
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
