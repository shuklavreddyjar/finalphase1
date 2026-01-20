package com.kirana.finalphase1.validator;
import com.kirana.finalphase1.exception.InsufficientBalanceException;
import com.kirana.finalphase1.exception.InvalidTransactionAmountException;

import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionValidator {

    public TransactionType validateType(String type) {
        try {
            return TransactionType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid transaction type");
        }
    }

    public void validateBalance(AccountEntity account,
                                TransactionType type,
                                BigDecimal amount) {

        if (type == TransactionType.DEBIT &&
                account.getBalance().compareTo(amount) < 0) {

            throw new InsufficientBalanceException("Insufficient balance");
        }
    }

    public void validateAmount(BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionAmountException(
                    "Transaction amount must be greater than zero"
            );
        }
    }


}
