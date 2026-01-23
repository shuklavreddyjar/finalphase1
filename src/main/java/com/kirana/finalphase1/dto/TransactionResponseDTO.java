package com.kirana.finalphase1.dto;
import com.kirana.finalphase1.enums.CurrencyType;
import com.kirana.finalphase1.enums.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * The type Transaction response dto.
 */
@Data
public class TransactionResponseDTO {

    private UUID transactionId;
    private String userId;
    private BigDecimal amount;
    private CurrencyType currency;
    private String type;
    private TransactionStatus status;
    private Date createdAt;


}
