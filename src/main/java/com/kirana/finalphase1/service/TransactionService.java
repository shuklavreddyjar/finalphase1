package com.kirana.finalphase1.service;

import com.kirana.finalphase1.dto.TransactionRequestDTO;
import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.enums.TransactionType;
import com.kirana.finalphase1.factory.TransactionFactory;
import com.kirana.finalphase1.repository.AccountRepository;
import com.kirana.finalphase1.repository.TransactionRepository;
import com.kirana.finalphase1.security.SecurityUtils;
import com.kirana.finalphase1.validator.TransactionValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * The type Transaction service.
 */
@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionValidator transactionValidator;
    private final CurrencyConversionService currencyConversionService;

    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            TransactionValidator transactionValidator,
            CurrencyConversionService currencyConversionService
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionValidator = transactionValidator;
        this.currencyConversionService = currencyConversionService;
    }

    /**
     * Wallet top-up / debit
     */
    @Transactional
    public TransactionEntity createTransaction(TransactionRequestDTO request) {


        transactionValidator.validateAmount(request.getAmount());


        TransactionType type =
                transactionValidator.validateType(request.getType());


        String userId = SecurityUtils.getCurrentUserId();

        AccountEntity account = accountRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException("Account not found"));


        BigDecimal amountInInr =
                currencyConversionService.convertToINR(
                        request.getAmount(),
                        request.getOriginalCurrency()
                );


        transactionValidator.validateBalance(
                account, type, amountInInr
        );


        BigDecimal newBalance =
                type == TransactionType.CREDIT
                        ? account.getBalance().add(amountInInr)
                        : account.getBalance().subtract(amountInInr);

        account.setBalance(newBalance);
        accountRepository.save(account);


        TransactionEntity transaction =
                TransactionFactory.create(
                        account,
                        type,
                        amountInInr,
                        request.getOriginalCurrency()
                );

        transactionRepository.save(transaction);

        return transaction;
    }
}
