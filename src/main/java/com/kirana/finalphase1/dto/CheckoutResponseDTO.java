package com.kirana.finalphase1.dto;

import lombok.Data;

import java.util.UUID;

/**
 * The type Checkout response dto.
 */
@Data
public class CheckoutResponseDTO {

    private UUID transactionId;
    private String status;
}
