package com.kirana.finalphase1.factory;

import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.enums.CurrencyType;
import com.kirana.finalphase1.enums.TransactionStatus;
import com.kirana.finalphase1.enums.TransactionType;

import java.math.BigDecimal;

/**
 * The type Transaction factory.
 */
public class TransactionFactory {

    /**
     * Create transaction entity.
     *
     * @param account          the account
     * @param type             CREDIT / DEBIT
     * @param amountInInr      amount always stored in INR
     * @param originalCurrency original currency sent by user
     * @return transaction entity
     */
    public static TransactionEntity create(
            AccountEntity account,
            TransactionType type,
            BigDecimal amountInInr,
            CurrencyType originalCurrency
    ) {

        TransactionEntity tx = new TransactionEntity();

        tx.setUserId(account.getUserId());
        tx.setAmount(amountInInr);
        tx.setOriginalCurrency(originalCurrency);
        tx.setType(type);
        tx.setStatus(TransactionStatus.SUCCESS);

        return tx;
    }
}
