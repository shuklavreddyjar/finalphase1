package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.ProductDocument;
import com.kirana.finalphase1.repository.mongo.ProductMongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryService {

    private final ProductMongoRepository productRepository;

    public ProductQueryService(ProductMongoRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDocument> getAllProducts() {
        return productRepository.findByActiveTrue();
    }

    public ProductDocument getProductById(String productId) {
        return productRepository.findById(productId)
                .filter(ProductDocument::isActive)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));
    }
}
