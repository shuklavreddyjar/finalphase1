package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.CartDocument;
import com.kirana.finalphase1.dto.AddToCartRequestDTO;
import com.kirana.finalphase1.entity.ProductEntity;
import com.kirana.finalphase1.repository.ProductRepository;
import com.kirana.finalphase1.repository.mongo.CartMongoRepository;
import com.kirana.finalphase1.security.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * The type Cart service.
 */
@Service
public class CartService {

    private final CartMongoRepository cartRepository;
    private final ProductRepository productRepository;

    /**
     * Instantiates a new Cart service.
     *
     * @param cartRepository    the cart repository
     * @param productRepository the product repository
     */
    public CartService(CartMongoRepository cartRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /**
     * Add to cart cart document.
     *
     * @param request the request
     * @return the cart document
     */
    public CartDocument addToCart(AddToCartRequestDTO request) {

        String userId = SecurityUtils.getCurrentUserId();

        // Fetch product (price snapshot)
        ProductEntity product = productRepository.findById(request.getProductId())
                .filter(ProductEntity::isActive)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));

        // Get or create ACTIVE cart
        CartDocument cart = cartRepository
                .findByUserIdAndStatus(userId, CartDocument.CartStatus.ACTIVE)
                .orElseGet(() -> {
                    CartDocument newCart = new CartDocument();
                    newCart.setUserId(userId);
                    return newCart;
                });

        // Check if product already in cart
        CartDocument.CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(product.getProductId().toString()))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new CartDocument.CartItem();
            item.setProductId(product.getProductId().toString());
            item.setQuantity(request.getQuantity());
            item.setPriceSnapshot(product.getPrice());
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + request.getQuantity());
        }

        return cartRepository.save(cart);
    }

    /**
     * View cart cart document.
     *
     * @return the cart document
     */
    public CartDocument viewCart() {

        String userId = SecurityUtils.getCurrentUserId();

        return cartRepository
                .findByUserIdAndStatus(userId, CartDocument.CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new IllegalStateException("Cart is empty"));
    }
}
