package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.dto.CheckoutResponseDTO;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.service.CheckoutService;
import org.springframework.web.bind.annotation.*;

/**
 * The type Checkout controller.
 */
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    /**
     * Instantiates a new Checkout controller.
     *
     * @param checkoutService the checkout service
     */
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    /**
     * Checkout checkout response dto.
     *
     * @return the checkout response dto
     */
    @PostMapping
    public CheckoutResponseDTO checkout() {

        TransactionEntity tx = checkoutService.checkout();

        CheckoutResponseDTO response = new CheckoutResponseDTO();
        response.setTransactionId(tx.getTransactionId());
        response.setStatus("SUCCESS");

        return response;
    }
}
