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

    /**
     * Instantiates a new Transaction service.
     *
     * @param accountRepository     the account repository
     * @param transactionRepository the transaction repository
     * @param transactionValidator  the transaction validator
     */
    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            TransactionValidator transactionValidator
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionValidator = transactionValidator;
    }

    /**
     * Wallet top-up & debit API
     *
     * @param request the request
     * @return the transaction entity
     */
    @Transactional
    public TransactionEntity createTransaction(TransactionRequestDTO request) {

        // Validate amount
        transactionValidator.validateAmount(request.getAmount());

        // Resolve transaction type
        TransactionType type =
                transactionValidator.validateType(request.getType());

        // Load user account
        String userId = SecurityUtils.getCurrentUserId();

        AccountEntity account = accountRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException("Account not found"));

        // Validate balance for DEBIT
        transactionValidator.validateBalance(
                account, type, request.getAmount()
        );

        // Update wallet balance
        BigDecimal newBalance =
                type == TransactionType.CREDIT
                        ? account.getBalance().add(request.getAmount())
                        : account.getBalance().subtract(request.getAmount());

        account.setBalance(newBalance);
        accountRepository.save(account);

        // Create transaction (FIXED CALL)
        TransactionEntity transaction =
                TransactionFactory.create(
                        account,
                        type,
                        request.getAmount()
                );

        transactionRepository.save(transaction);

        return transaction;
    }
}
