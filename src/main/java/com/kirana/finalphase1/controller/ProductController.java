package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.document.ProductDocument;
import com.kirana.finalphase1.service.ProductQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<ProductDocument> getAllProducts() {
        return productQueryService.getAllProducts();
    }

    /**
     * GET product by ID (public)
     *
     * @param productId the product id (Mongo ObjectId hex string)
     * @return the product
     */
    @GetMapping("/{productId}")
    public ProductDocument getProductById(
            @PathVariable String productId) {

        return productQueryService.getProductById(productId);
    }
}
