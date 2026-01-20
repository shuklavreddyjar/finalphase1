package com.kirana.finalphase1.factory;

import com.kirana.finalphase1.dto.TransactionRequestDTO;
import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.enums.TransactionStatus;
import com.kirana.finalphase1.enums.TransactionType;

import java.math.BigDecimal;

public class TransactionFactory {

    public static TransactionEntity create(
            AccountEntity account,
            TransactionRequestDTO request,
            TransactionType type,
            BigDecimal amount) {

        TransactionEntity tx = new TransactionEntity();

        tx.setUserId(account.getUserId());
        tx.setAmount(amount);
        tx.setOriginalCurrency(request.getOriginalCurrency());
        tx.setType(type);
        tx.setStatus(TransactionStatus.SUCCESS);

        return tx;
    }
}
