package com.kirana.finalphase1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

/**
 * The type Add to cart request dto.
 */
@Data
public class AddToCartRequestDTO {

    @NotNull
    private UUID productId;

    @NotNull
    @Positive
    private Integer quantity;
}
