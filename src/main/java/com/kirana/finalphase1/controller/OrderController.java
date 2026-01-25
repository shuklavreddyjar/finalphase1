package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.document.OrderDocument;
import com.kirana.finalphase1.service.OrderQueryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderQueryService orderQueryService;

    public OrderController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    // USER: view own orders
    @GetMapping
    public List<OrderDocument> getMyOrders() {
        return orderQueryService.getMyOrders();
    }

    // ADMIN: view any order
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDocument getOrderById(@PathVariable String orderId) {
        return orderQueryService.getOrderById(orderId);
    }
}
