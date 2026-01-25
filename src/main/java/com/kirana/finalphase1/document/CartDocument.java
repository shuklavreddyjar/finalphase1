package com.kirana.finalphase1.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "carts")
@Data
public class CartDocument {

    @Id
    @JsonIgnore
    private ObjectId id;

    private String userId;

    private CartStatus status = CartStatus.ACTIVE;

    private List<CartItem> items = new ArrayList<>();


    @JsonProperty("cartId")
    public String getCartId() {
        return id != null ? id.toHexString() : null;
    }



    public enum CartStatus {
        ACTIVE,
        CHECKED_OUT
    }



    @Data
    public static class CartItem {

        private String productId;        // Mongo productId as hex string
        private Integer quantity;
        private BigDecimal priceSnapshot;
    }
}
