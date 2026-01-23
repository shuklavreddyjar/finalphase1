package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.dto.CreateProductRequestDTO;
import com.kirana.finalphase1.entity.ProductEntity;
import com.kirana.finalphase1.service.ProductAdminService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * The type Admin product controller.
 */
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductAdminService productAdminService;

    /**
     * Instantiates a new Admin product controller.
     *
     * @param productAdminService the product admin service
     */
    public AdminProductController(ProductAdminService productAdminService) {
        this.productAdminService = productAdminService;
    }

    /**
     * Create product map.
     *
     * @param request the request
     * @return the map
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> createProduct(
            @Valid @RequestBody CreateProductRequestDTO request) {

        ProductEntity product =
                productAdminService.createProduct(request);

        return Map.of(
                "productId", product.getProductId(),
                "status", "CREATED"
        );
    }
}
