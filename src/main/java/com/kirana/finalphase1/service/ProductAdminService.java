package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.ProductDocument;
import com.kirana.finalphase1.dto.CreateProductRequestDTO;
import com.kirana.finalphase1.entity.InventoryEntity;
import com.kirana.finalphase1.repository.InventoryRepository;
import com.kirana.finalphase1.repository.mongo.ProductMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductAdminService {

    private final ProductMongoRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @Transactional
    public ProductDocument createProduct(CreateProductRequestDTO request) {

        // 1. Create product (Mongo)
        ProductDocument product = new ProductDocument();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCurrency(request.getCurrency());
        product.setActive(true);

        ProductDocument savedProduct = productRepository.save(product);

        // 2. Create inventory (Postgres)
        InventoryEntity inventory = new InventoryEntity();
        inventory.setProductId(savedProduct.getId().toHexString());
        inventory.setQuantityAvailable(request.getInitialQuantity());

        inventoryRepository.save(inventory);

        return savedProduct;
    }
}
