package com.kirana.finalphase1.entity;

import com.kirana.finalphase1.enums.CurrencyType;
import com.kirana.finalphase1.enums.TransactionStatus;
import com.kirana.finalphase1.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * The type Transaction entity.
 */
@Entity
@Table(name = "transactions")
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private UUID transactionId;

    /**
     * MongoDB User _id (ObjectId) stored as String.
     * Example: "696a891f21eac07e16ad4cab"
     */
    @Column(name = "user_id", nullable = false, length = 24)
    private String userId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "original_currency", nullable = false, length = 3)
    private CurrencyType originalCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
}
