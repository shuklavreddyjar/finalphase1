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
     * @param account the account
     * @param type    the type
     * @param amount  the amount
     * @return the transaction entity
     */
    public static TransactionEntity create(
            AccountEntity account,
            TransactionType type,
            BigDecimal amount) {

        TransactionEntity tx = new TransactionEntity();

        tx.setUserId(account.getUserId());
        tx.setAmount(amount);
        tx.setOriginalCurrency(CurrencyType.INR);
        tx.setType(type);
        tx.setStatus(TransactionStatus.SUCCESS);

        return tx;
    }
}
