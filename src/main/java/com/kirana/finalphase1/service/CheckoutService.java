package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.CartDocument;
import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.entity.InventoryEntity;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.enums.TransactionType;
import com.kirana.finalphase1.factory.TransactionFactory;
import com.kirana.finalphase1.repository.AccountRepository;
import com.kirana.finalphase1.repository.InventoryRepository;
import com.kirana.finalphase1.repository.TransactionRepository;
import com.kirana.finalphase1.repository.mongo.CartMongoRepository;
import com.kirana.finalphase1.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The type Checkout service.
 */
@Service
public class CheckoutService {

    private final CartMongoRepository cartRepository;
    private final InventoryRepository inventoryRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Instantiates a new Checkout service.
     *
     * @param cartRepository        the cart repository
     * @param inventoryRepository   the inventory repository
     * @param accountRepository     the account repository
     * @param transactionRepository the transaction repository
     */
    public CheckoutService(
            CartMongoRepository cartRepository,
            InventoryRepository inventoryRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository
    ) {
        this.cartRepository = cartRepository;
        this.inventoryRepository = inventoryRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Performs checkout for the currently authenticated user.
     * This method is ATOMIC.
     *
     * @return the transaction entity
     */
    @Transactional
    public TransactionEntity checkout() {

        // Get authenticated userId (Mongo ObjectId string)
        String userId = SecurityUtils.getCurrentUserId();

        // Fetch ACTIVE cart
        CartDocument cart = cartRepository
                .findByUserIdAndStatus(userId, CartDocument.CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("No active cart found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        // Calculate total amount & validate inventory
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartDocument.CartItem item : cart.getItems()) {

            UUID productId = UUID.fromString(item.getProductId());

            InventoryEntity inventory = inventoryRepository
                    .findById(productId)
                    .orElseThrow(() ->
                            new IllegalStateException("Inventory not found for product"));

            if (inventory.getQuantityAvailable() < item.getQuantity()) {
                throw new IllegalStateException("Insufficient inventory");
            }

            // Deduct inventory
            inventory.setQuantityAvailable(
                    inventory.getQuantityAvailable() - item.getQuantity()
            );
            inventoryRepository.save(inventory);

            // Add to total using PRICE SNAPSHOT
            totalAmount = totalAmount.add(
                    item.getPriceSnapshot()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        // Load user wallet account
        AccountEntity account = accountRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException("Account not found"));

        // Verify wallet balance
        if (account.getBalance().compareTo(totalAmount) < 0) {
            throw new IllegalStateException("Insufficient wallet balance");
        }

        // Debit wallet
        account.setBalance(account.getBalance().subtract(totalAmount));
        accountRepository.save(account);

        // Create DEBIT transaction (INR)
        TransactionEntity transaction =
                TransactionFactory.create(
                        account,
                        TransactionType.DEBIT,
                        totalAmount
                );

        transactionRepository.saveAndFlush(transaction);

        // Mark cart as CHECKED_OUT
        cart.setStatus(CartDocument.CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return transaction;
    }
}
