package com.kirana.finalphase1.dto;
import com.kirana.finalphase1.enums.CurrencyType;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class TransactionRequestDTO {


    private BigDecimal amount;
    private CurrencyType originalCurrency;
    private String type;


}
