package com.kirana.finalphase1.service;

import com.kirana.finalphase1.dto.CreateProductRequestDTO;
import com.kirana.finalphase1.entity.InventoryEntity;
import com.kirana.finalphase1.entity.ProductEntity;
import com.kirana.finalphase1.repository.InventoryRepository;
import com.kirana.finalphase1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Product admin service.
 */
@Service
@RequiredArgsConstructor
public class ProductAdminService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    /**
     * Create product product entity.
     *
     * @param request the request
     * @return the product entity
     */
    @Transactional
    public ProductEntity createProduct(CreateProductRequestDTO request) {

        // Create product
        ProductEntity product = new ProductEntity();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCurrency(request.getCurrency());
        product.setActive(true);

        ProductEntity savedProduct = productRepository.save(product);

        // Create inventory
        InventoryEntity inventory = new InventoryEntity();
        inventory.setProductId(savedProduct.getProductId());
        inventory.setQuantityAvailable(request.getInitialQuantity());

        inventoryRepository.save(inventory);

        return savedProduct;
    }
}
