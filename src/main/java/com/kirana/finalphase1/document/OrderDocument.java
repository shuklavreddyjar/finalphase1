package com.kirana.finalphase1.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "orders")
public class OrderDocument {

    @Id
    private ObjectId id;

    // MongoDB userId (ObjectId hex string)
    private String userId;

    private List<OrderItem> items;

    private BigDecimal totalAmount;

    private OrderStatus status;

    @CreatedDate
    private Date createdAt;

    @Data
    public static class OrderItem {
        private String productId;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal;
    }

    public enum OrderStatus {
        PLACED,
        PAID,
        CANCELLED
    }
}
