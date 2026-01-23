package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.document.CartDocument;
import com.kirana.finalphase1.dto.AddToCartRequestDTO;
import com.kirana.finalphase1.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * The type Cart controller.
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    /**
     * Instantiates a new Cart controller.
     *
     * @param cartService the cart service
     */
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * ADD PRODUCT TO CART
     *
     * @param request the request
     * @return the cart document
     */
    @PostMapping("/add")
    public CartDocument addToCart(
            @Valid @RequestBody AddToCartRequestDTO request) {

        return cartService.addToCart(request);
    }

    /**
     * VIEW CART
     *
     * @return the cart document
     */
    @GetMapping
    public CartDocument viewCart() {
        return cartService.viewCart();
    }
}
