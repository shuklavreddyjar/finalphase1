package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.OrderDocument;
import com.kirana.finalphase1.repository.mongo.OrderMongoRepository;
import com.kirana.finalphase1.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderQueryService {

    private final OrderMongoRepository orderRepository;

    public OrderQueryService(OrderMongoRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // USER: view own orders
    public List<OrderDocument> getMyOrders() {
        String userId = SecurityUtils.getCurrentUserId();
        return orderRepository.findByUserId(userId);
    }

    // ADMIN: view any order
    public OrderDocument getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Order not found"));
    }
}
