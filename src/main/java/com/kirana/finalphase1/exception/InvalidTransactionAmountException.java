package com.kirana.finalphase1.exception;

public class InvalidTransactionAmountException extends RuntimeException {

    public InvalidTransactionAmountException(String message) {
        super(message);
    }
}
