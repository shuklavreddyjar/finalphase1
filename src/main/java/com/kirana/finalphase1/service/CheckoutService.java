package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.CartDocument;
import com.kirana.finalphase1.document.OrderDocument;
import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.entity.InventoryEntity;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.enums.CurrencyType;
import com.kirana.finalphase1.enums.TransactionType;
import com.kirana.finalphase1.factory.TransactionFactory;
import com.kirana.finalphase1.repository.AccountRepository;
import com.kirana.finalphase1.repository.InventoryRepository;
import com.kirana.finalphase1.repository.TransactionRepository;
import com.kirana.finalphase1.repository.mongo.CartMongoRepository;
import com.kirana.finalphase1.repository.mongo.OrderMongoRepository;
import com.kirana.finalphase1.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Checkout service.
 */
@Service
public class CheckoutService {

    private final CartMongoRepository cartRepository;
    private final InventoryRepository inventoryRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final OrderMongoRepository orderRepository;

    public CheckoutService(
            CartMongoRepository cartRepository,
            InventoryRepository inventoryRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            OrderMongoRepository orderRepository
    ) {
        this.cartRepository = cartRepository;
        this.inventoryRepository = inventoryRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Atomic checkout
     */
    @Transactional
    public TransactionEntity checkout() {

        String userId = SecurityUtils.getCurrentUserId();


        CartDocument cart = cartRepository
                .findByUserIdAndStatus(userId, CartDocument.CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("No active cart found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;


        for (CartDocument.CartItem item : cart.getItems()) {

            InventoryEntity inventory = inventoryRepository
                    .findByProductId(item.getProductId())
                    .orElseThrow(() ->
                            new IllegalStateException("Inventory not found"));


            if (inventory.getQuantityAvailable() < item.getQuantity()) {
                throw new IllegalStateException("Insufficient inventory");
            }

            inventory.setQuantityAvailable(
                    inventory.getQuantityAvailable() - item.getQuantity()
            );

            inventoryRepository.save(inventory);

            totalAmount = totalAmount.add(
                    item.getPriceSnapshot()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }


        AccountEntity account = accountRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException("Account not found"));

        if (account.getBalance().compareTo(totalAmount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(totalAmount));
        accountRepository.save(account);


        TransactionEntity tx =
                TransactionFactory.create(
                        account,
                        TransactionType.DEBIT,
                        totalAmount,
                        CurrencyType.INR
                );

        transactionRepository.save(tx);


        OrderDocument order = new OrderDocument();
        order.setUserId(userId);
        order.setStatus(OrderDocument.OrderStatus.PAID);
        order.setTotalAmount(totalAmount);

        List<OrderDocument.OrderItem> orderItems = new ArrayList<>();

        for (CartDocument.CartItem item : cart.getItems()) {
            OrderDocument.OrderItem oi = new OrderDocument.OrderItem();
            oi.setProductId(item.getProductId());
            oi.setQuantity(item.getQuantity());
            oi.setPrice(item.getPriceSnapshot());
            oi.setSubtotal(
                    item.getPriceSnapshot()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
            orderItems.add(oi);
        }

        order.setItems(orderItems);
        orderRepository.save(order);


        cart.setStatus(CartDocument.CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return tx;
    }
}
