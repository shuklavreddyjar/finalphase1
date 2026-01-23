package com.kirana.finalphase1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * The type Global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle duplicate user response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
// DUPLICATE USER (SIGNUP)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateUser(
            UserAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", 400,
                        "message", ex.getMessage()
                ));
    }

    /**
     * Handle invalid credentials response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
// INVALID LOGIN (EMAIL / PASSWORD)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(
            InvalidCredentialsException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "status", 401,
                        "message", ex.getMessage()
                ));
    }

    /**
     * Handle insufficient balance response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
// INSUFFICIENT BALANCE
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientBalance(
            InsufficientBalanceException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", 400,
                        "message", ex.getMessage()
                ));
    }

    /**
     * Handle invalid amount response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
// INVALID TRANSACTION AMOUNT
    @ExceptionHandler(InvalidTransactionAmountException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidAmount(
            InvalidTransactionAmountException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", 400,
                        "message", ex.getMessage()
                ));
    }

    /**
     * Handle validation errors response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
//  VALIDATION ERRORS (EMPTY EMAIL / PASSWORD)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) //  FIXED
                .body(Map.of(
                        "status", 400,
                        "message", "Invalid username or password"
                ));
    }

    /**
     * Handle illegal argument response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
// ILLEGAL ARGUMENTS
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", 400,
                        "message", ex.getMessage()
                ));
    }

    /**
     * Handle account not found response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotFound(
            AccountNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", 400,
                        "message", ex.getMessage()
                ));
    }

    /**
     * Handle illegal state response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(
            IllegalStateException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", 400,
                        "message", ex.getMessage()
                ));
    }




}
