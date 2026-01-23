package com.kirana.finalphase1.exception;

/**
 * The type User already exists exception.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Instantiates a new User already exists exception.
     *
     * @param message the message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
