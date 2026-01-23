package com.kirana.finalphase1.dto;

import com.kirana.finalphase1.enums.CurrencyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The type Create product request dto.
 */
@Data
public class CreateProductRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private CurrencyType currency;

    @NotNull
    @Positive
    private Integer initialQuantity;
}
