package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.entity.ProductEntity;
import com.kirana.finalphase1.service.ProductQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The type Product controller.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductQueryService productQueryService;

    /**
     * Instantiates a new Product controller.
     *
     * @param productQueryService the product query service
     */
    public ProductController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    /**
     * GET all products (public)
     *
     * @return the all products
     */
    @GetMapping
    public List<ProductEntity> getAllProducts() {
        return productQueryService.getAllProducts();
    }

    /**
     * GET product by ID (public)
     *
     * @param productId the product id
     * @return the product by id
     */
    @GetMapping("/{productId}")
    public ProductEntity getProductById(
            @PathVariable UUID productId) {

        return productQueryService.getProductById(productId);
    }
}
