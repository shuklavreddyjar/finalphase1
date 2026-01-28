package com.kirana.finalphase1.validator;

import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.enums.TransactionType;
import com.kirana.finalphase1.exception.InsufficientBalanceException;
import com.kirana.finalphase1.exception.InvalidTransactionAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionValidatorTest {

    private TransactionValidator transactionValidator;

    @BeforeEach
    void setUp() {
        transactionValidator = new TransactionValidator();
    }

    // =========================
    // validateAmount tests
    // =========================

    @Test
    void validateAmount_shouldFail_whenAmountIsNull() {
        assertThrows(
                InvalidTransactionAmountException.class,
                () -> transactionValidator.validateAmount(null)
        );
    }

    @Test
    void validateAmount_shouldFail_whenAmountIsZero() {
        assertThrows(
                InvalidTransactionAmountException.class,
                () -> transactionValidator.validateAmount(BigDecimal.ZERO)
        );
    }

    @Test
    void validateAmount_shouldFail_whenAmountIsNegative() {
        assertThrows(
                InvalidTransactionAmountException.class,
                () -> transactionValidator.validateAmount(new BigDecimal("-10"))
        );
    }

    @Test
    void validateAmount_shouldPass_whenAmountIsPositive() {
        assertDoesNotThrow(
                () -> transactionValidator.validateAmount(new BigDecimal("100"))
        );
    }

    // =========================
    // validateType tests
    // =========================

    @Test
    void validateType_shouldReturnCREDIT_whenInputIsCreditLowercase() {
        TransactionType type =
                transactionValidator.validateType("credit");

        assertEquals(TransactionType.CREDIT, type);
    }

    @Test
    void validateType_shouldReturnDEBIT_whenInputIsDebitUppercase() {
        TransactionType type =
                transactionValidator.validateType("DEBIT");

        assertEquals(TransactionType.DEBIT, type);
    }

    @Test
    void validateType_shouldFail_whenTypeIsInvalid() {
        assertThrows(
                IllegalArgumentException.class,
                () -> transactionValidator.validateType("RANDOM")
        );
    }

    @Test
    void validateType_shouldFail_whenTypeIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> transactionValidator.validateType(null)
        );
    }

    // =========================
    // validateBalance tests
    // =========================

    @Test
    void validateBalance_shouldPass_forCredit_evenIfBalanceIsZero() {
        AccountEntity account = new AccountEntity();
        account.setBalance(BigDecimal.ZERO);

        assertDoesNotThrow(() ->
                transactionValidator.validateBalance(
                        account,
                        TransactionType.CREDIT,
                        new BigDecimal("100")
                )
        );
    }

    @Test
    void validateBalance_shouldPass_forDebit_whenBalanceIsEnough() {
        AccountEntity account = new AccountEntity();
        account.setBalance(new BigDecimal("500"));

        assertDoesNotThrow(() ->
                transactionValidator.validateBalance(
                        account,
                        TransactionType.DEBIT,
                        new BigDecimal("100")
                )
        );
    }

    @Test
    void validateBalance_shouldFail_forDebit_whenBalanceIsInsufficient() {
        AccountEntity account = new AccountEntity();
        account.setBalance(new BigDecimal("50"));

        assertThrows(
                InsufficientBalanceException.class,
                () -> transactionValidator.validateBalance(
                        account,
                        TransactionType.DEBIT,
                        new BigDecimal("100")
                )
        );
    }
}
