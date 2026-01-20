package com.kirana.finalphase1.service;

import com.kirana.finalphase1.dto.TransactionRequestDTO;
import com.kirana.finalphase1.dto.TransactionResponseDTO;
import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.enums.TransactionType;
import com.kirana.finalphase1.factory.TransactionFactory;
import com.kirana.finalphase1.mapper.TransactionResponseMapper;
import com.kirana.finalphase1.repository.AccountRepository;
import com.kirana.finalphase1.repository.TransactionRepository;
import com.kirana.finalphase1.validator.TransactionValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Responsibilities:
 * - Validate transaction request
 * - Load user account (by userId)
 * - Convert foreign currency to INR
 * - Validate account balance
 * - Apply balance updates
 * - Persist transaction data
 */
@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CurrencyConversionService currencyConversionService;
    private final TransactionValidator transactionValidator;
    private final TransactionResponseMapper responseMapper;

    public TransactionService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository,
                              CurrencyConversionService currencyConversionService,
                              TransactionValidator transactionValidator,
                              TransactionResponseMapper responseMapper) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.currencyConversionService = currencyConversionService;
        this.transactionValidator = transactionValidator;
        this.responseMapper = responseMapper;
    }

    @Transactional
    public TransactionResponseDTO createTransaction(
            String userId,                     // 🔥 Mongo ObjectId (hex string)
            TransactionRequestDTO request) {

        // Validate transaction amount
        transactionValidator.validateAmount(request.getAmount());

        // Validate transaction type (CREDIT / DEBIT)
        TransactionType type =
                transactionValidator.validateType(request.getType());

        if (request.getOriginalCurrency() == null) {
            throw new IllegalArgumentException("Currency is required");
        }

        // Load account using userId (NOT email)
        AccountEntity account = accountRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException("Account not found for user"));

        // Convert currency to INR
        BigDecimal inrAmount =
                currencyConversionService.convertToINR(
                        request.getAmount(),
                        request.getOriginalCurrency()
                );

        // Validate sufficient balance (only for DEBIT)
        transactionValidator.validateBalance(account, type, inrAmount);

        // Apply balance update
        applyBalance(account, type, inrAmount);
        accountRepository.save(account);

        // Create and persist transaction record
        TransactionEntity transaction =
                TransactionFactory.create(account, request, type, inrAmount);

        transactionRepository.saveAndFlush(transaction);

        // Build response DTO
        return responseMapper.success(transaction);
    }

    /**
     * Applies balance changes based on transaction type.
     */
    private void applyBalance(AccountEntity account,
                              TransactionType type,
                              BigDecimal amount) {

        if (type == TransactionType.DEBIT) {
            account.setBalance(account.getBalance().subtract(amount));
        } else {
            account.setBalance(account.getBalance().add(amount));
        }
    }
}
