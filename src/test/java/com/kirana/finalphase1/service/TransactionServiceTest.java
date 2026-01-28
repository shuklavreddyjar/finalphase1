package com.kirana.finalphase1.service;

import com.kirana.finalphase1.dto.TransactionRequestDTO;
import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.enums.CurrencyType;
import com.kirana.finalphase1.enums.TransactionType;
import com.kirana.finalphase1.exception.InsufficientBalanceException;
import com.kirana.finalphase1.repository.AccountRepository;
import com.kirana.finalphase1.repository.TransactionRepository;
import com.kirana.finalphase1.validator.TransactionValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionValidator transactionValidator;

    @Mock
    private CurrencyConversionService currencyConversionService;

    @InjectMocks
    private TransactionService transactionService;

    // ---------- SECURITY CONTEXT MOCK ----------
    private void mockAuthenticatedUser(String userId) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userId, null, List.of());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    // ---------- CREDIT TEST ----------
    @Test
    void creditUsd_shouldConvertToInr_andIncreaseBalance() {

        mockAuthenticatedUser("user1");

        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setAmount(new BigDecimal("100"));
        request.setOriginalCurrency(CurrencyType.USD);
        request.setType("CREDIT");

        AccountEntity account = new AccountEntity();
        account.setUserId("user1");
        account.setBalance(BigDecimal.ZERO);

        when(accountRepository.findByUserId(any()))
                .thenReturn(Optional.of(account));

        when(transactionValidator.validateType("CREDIT"))
                .thenReturn(TransactionType.CREDIT);

        when(currencyConversionService.convertToINR(any(), any()))
                .thenReturn(new BigDecimal("8300"));

        transactionService.createTransaction(request);

        assertEquals(new BigDecimal("8300"), account.getBalance());
    }

    // ---------- DEBIT FAILURE TEST ----------
    @Test
    void debit_shouldFail_whenInsufficientBalance() {

        mockAuthenticatedUser("user1");

        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setAmount(new BigDecimal("500"));
        request.setOriginalCurrency(CurrencyType.INR);
        request.setType("DEBIT");

        AccountEntity account = new AccountEntity();
        account.setUserId("user1");
        account.setBalance(new BigDecimal("100"));

        when(accountRepository.findByUserId(any()))
                .thenReturn(Optional.of(account));

        when(transactionValidator.validateType("DEBIT"))
                .thenReturn(TransactionType.DEBIT);

        when(currencyConversionService.convertToINR(any(), any()))
                .thenReturn(new BigDecimal("500"));


        doThrow(new InsufficientBalanceException("Insufficient balance"))
                .when(transactionValidator)
                .validateBalance(any(), any(), any());

        assertThrows(
                InsufficientBalanceException.class,
                () -> transactionService.createTransaction(request)
        );
    }
}
