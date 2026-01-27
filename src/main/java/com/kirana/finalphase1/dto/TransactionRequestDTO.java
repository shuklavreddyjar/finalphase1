package com.kirana.finalphase1.dto;

import com.kirana.finalphase1.enums.CurrencyType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * The type Transaction request dto.
 */
@Data
public class TransactionRequestDTO {

    private BigDecimal amount;

    /**
     * Currency in which user is sending money (USD / INR)
     */
    private CurrencyType originalCurrency;

    /**
     * CREDIT / DEBIT
     */
    private String type;
}
