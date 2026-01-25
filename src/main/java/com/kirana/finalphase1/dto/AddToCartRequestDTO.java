package com.kirana.finalphase1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * The type Add to cart request dto.
 */
@Data
public class AddToCartRequestDTO {

    @NotNull
    private String productId;   // Mongo ObjectId as String

    @NotNull
    @Positive
    private Integer quantity;
}
