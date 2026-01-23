package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.dto.TransactionRequestDTO;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Transaction controller.
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Instantiates a new Transaction controller.
     *
     * @param transactionService the transaction service
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Wallet top-up / debit
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<TransactionEntity> createTransaction(
            @RequestBody TransactionRequestDTO request
    ) {
        TransactionEntity transaction =
                transactionService.createTransaction(request);

        return ResponseEntity.ok(transaction);
    }
}
