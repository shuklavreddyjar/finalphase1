package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.CartDocument;
import com.kirana.finalphase1.document.ProductDocument;
import com.kirana.finalphase1.dto.AddToCartRequestDTO;
import com.kirana.finalphase1.entity.InventoryEntity;
import com.kirana.finalphase1.repository.InventoryRepository;
import com.kirana.finalphase1.repository.mongo.CartMongoRepository;
import com.kirana.finalphase1.repository.mongo.ProductMongoRepository;
import com.kirana.finalphase1.security.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartMongoRepository cartRepository;
    private final ProductMongoRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public CartService(
            CartMongoRepository cartRepository,
            ProductMongoRepository productRepository,
            InventoryRepository inventoryRepository
    ) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public CartDocument addToCart(AddToCartRequestDTO request) {

        String userId = SecurityUtils.getCurrentUserId();

        ProductDocument product = productRepository
                .findById(request.getProductId())
                .filter(ProductDocument::isActive)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));

        InventoryEntity inventory = inventoryRepository
                .findById(product.getId().toHexString())
                .orElseThrow(() ->
                        new IllegalStateException("Inventory not found"));

        if (inventory.getQuantityAvailable() < request.getQuantity()) {
            throw new IllegalStateException("Insufficient inventory");
        }

        CartDocument cart = cartRepository
                .findByUserIdAndStatus(userId, CartDocument.CartStatus.ACTIVE)
                .orElseGet(() -> {
                    CartDocument c = new CartDocument();
                    c.setUserId(userId);
                    return c;
                });

        CartDocument.CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(product.getId().toHexString()))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new CartDocument.CartItem();
            item.setProductId(product.getId().toHexString());
            item.setQuantity(request.getQuantity());
            item.setPriceSnapshot(product.getPrice());
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + request.getQuantity());
        }

        return cartRepository.save(cart);
    }

    public CartDocument viewCart() {

        String userId = SecurityUtils.getCurrentUserId();

        return cartRepository
                .findByUserIdAndStatus(userId, CartDocument.CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("Cart is empty"));
    }
}
