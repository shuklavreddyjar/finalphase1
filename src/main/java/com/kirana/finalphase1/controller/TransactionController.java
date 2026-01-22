package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.dto.TransactionRequestDTO;
import com.kirana.finalphase1.dto.TransactionResponseDTO;
import com.kirana.finalphase1.security.SecurityUtils;
import com.kirana.finalphase1.service.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionResponseDTO createTransaction(
            @RequestBody TransactionRequestDTO request) {

        // userId = MongoDB ObjectId (hex string) from JWT
        String userId = SecurityUtils.getCurrentUserId();

        return transactionService.createTransaction(userId, request);
    }
}
