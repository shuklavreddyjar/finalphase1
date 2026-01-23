package com.kirana.finalphase1.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Cart document.
 */
@Data
@Document(collection = "carts")
public class CartDocument {

    @Id
    private ObjectId cartId;

    /**
     * MongoDB User _id (hex string)
     */
    private String userId;

    private CartStatus status = CartStatus.ACTIVE;

    private List<CartItem> items = new ArrayList<>();

    /**
     * The type Cart item.
     */
    @Data
    public static class CartItem {
        private String productId;
        private Integer quantity;
        private BigDecimal priceSnapshot;
    }

    /**
     * The enum Cart status.
     */
    public enum CartStatus {
        /**
         * Active cart status.
         */
        ACTIVE,
        /**
         * Checked out cart status.
         */
        CHECKED_OUT
    }
}
