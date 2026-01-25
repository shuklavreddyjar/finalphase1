package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.document.ProductDocument;
import com.kirana.finalphase1.dto.CreateProductRequestDTO;
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

    public AdminProductController(ProductAdminService productAdminService) {
        this.productAdminService = productAdminService;
    }

    /**
     * CREATE PRODUCT (ADMIN ONLY)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> createProduct(
            @Valid @RequestBody CreateProductRequestDTO request) {

        ProductDocument product =
                productAdminService.createProduct(request);

        return Map.of(
                "productId", product.getId().toHexString(),
                "status", "CREATED"
        );
    }
}
