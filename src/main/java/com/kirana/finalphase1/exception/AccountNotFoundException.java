package com.kirana.finalphase1.exception;

/**
 * The type Account not found exception.
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Account not found exception.
     *
     * @param message the message
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
