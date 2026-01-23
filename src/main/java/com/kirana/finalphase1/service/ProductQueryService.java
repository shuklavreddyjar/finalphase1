package com.kirana.finalphase1.service;

import com.kirana.finalphase1.entity.ProductEntity;
import com.kirana.finalphase1.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Product query service.
 */
@Service
public class ProductQueryService {

    private final ProductRepository productRepository;

    /**
     * Instantiates a new Product query service.
     *
     * @param productRepository the product repository
     */
    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Fetch all active products
     *
     * @return the all products
     */
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .filter(ProductEntity::isActive)
                .toList();
    }

    /**
     * Fetch single active product by ID
     *
     * @param productId the product id
     * @return the product by id
     */
    public ProductEntity getProductById(UUID productId) {
        return productRepository.findById(productId)
                .filter(ProductEntity::isActive)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));
    }
}
