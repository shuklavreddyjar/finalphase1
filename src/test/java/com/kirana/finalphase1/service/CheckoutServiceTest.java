package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.CartDocument;
import com.kirana.finalphase1.document.OrderDocument;
import com.kirana.finalphase1.entity.AccountEntity;
import com.kirana.finalphase1.entity.InventoryEntity;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.repository.AccountRepository;
import com.kirana.finalphase1.repository.InventoryRepository;
import com.kirana.finalphase1.repository.TransactionRepository;
import com.kirana.finalphase1.repository.mongo.CartMongoRepository;
import com.kirana.finalphase1.repository.mongo.OrderMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    private static final String USER_ID = "user-123";
    private static final String PRODUCT_ID = "product-1";

    @Mock
    private CartMongoRepository cartRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private OrderMongoRepository orderRepository;

    @InjectMocks
    private CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(USER_ID, null, List.of())
        );
    }

    // ------------------------
    // SUCCESS CASE
    // ------------------------
    @Test
    void checkout_success_shouldCreateOrderDebitWalletAndCloseCart() {

        mockActiveCart(2, new BigDecimal("100"));
        mockInventory(PRODUCT_ID, 10);
        mockAccount(new BigDecimal("500"));

        TransactionEntity result = checkoutService.checkout();

        assertNotNull(result);

        verify(cartRepository).save(any(CartDocument.class));
        verify(orderRepository).save(any(OrderDocument.class));
        verify(accountRepository).save(any(AccountEntity.class));
        verify(transactionRepository).save(any(TransactionEntity.class));
    }

    // ------------------------
    // FAILING CASES
    // ------------------------

    @Test
    void checkout_shouldFail_whenNoActiveCart() {

        when(cartRepository.findByUserIdAndStatus(
                USER_ID, CartDocument.CartStatus.ACTIVE))
                .thenReturn(Optional.empty());

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> checkoutService.checkout()
        );

        assertEquals("No active cart found", ex.getMessage());
    }

    @Test
    void checkout_shouldFail_whenCartIsEmpty() {

        CartDocument cart = new CartDocument();
        cart.setUserId(USER_ID);
        cart.setStatus(CartDocument.CartStatus.ACTIVE);

        when(cartRepository.findByUserIdAndStatus(
                USER_ID, CartDocument.CartStatus.ACTIVE))
                .thenReturn(Optional.of(cart));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> checkoutService.checkout()
        );

        assertEquals("Cart is empty", ex.getMessage());
    }

    @Test
    void checkout_shouldFail_whenInventoryInsufficient() {

        mockActiveCart(5, new BigDecimal("100"));
        mockInventory(PRODUCT_ID, 2); // insufficient

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> checkoutService.checkout()
        );

        assertEquals("Insufficient inventory", ex.getMessage());
    }

    @Test
    void checkout_shouldFail_whenWalletBalanceInsufficient() {

        mockActiveCart(3, new BigDecimal("100"));
        mockInventory(PRODUCT_ID, 10);
        mockAccount(new BigDecimal("100")); // insufficient balance

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> checkoutService.checkout()
        );

        assertEquals("Insufficient balance", ex.getMessage());
    }

    // ------------------------
    // HELPER METHODS
    // ------------------------

    private void mockActiveCart(int quantity, BigDecimal price) {

        CartDocument cart = new CartDocument();
        cart.setUserId(USER_ID);
        cart.setStatus(CartDocument.CartStatus.ACTIVE);

        CartDocument.CartItem item = new CartDocument.CartItem();
        item.setProductId(PRODUCT_ID);
        item.setQuantity(quantity);
        item.setPriceSnapshot(price);

        cart.setItems(List.of(item));

        when(cartRepository.findByUserIdAndStatus(
                USER_ID, CartDocument.CartStatus.ACTIVE))
                .thenReturn(Optional.of(cart));
    }

    private void mockInventory(String productId, int qty) {

        InventoryEntity inventory = new InventoryEntity();
        inventory.setProductId(productId);
        inventory.setQuantityAvailable(qty);

        when(inventoryRepository.findByProductId(productId))
                .thenReturn(Optional.of(inventory));
    }

    private void mockAccount(BigDecimal balance) {

        AccountEntity account = new AccountEntity();
        account.setUserId(USER_ID);
        account.setBalance(balance);

        when(accountRepository.findByUserId(USER_ID))
                .thenReturn(Optional.of(account));
    }
}
