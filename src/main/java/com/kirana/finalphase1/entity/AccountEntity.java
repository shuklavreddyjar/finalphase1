package com.kirana.finalphase1.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "accounts")
@Data
public class AccountEntity {

    /**
     * This stores MongoDB User _id (ObjectId) as a String.
     * Example: "696a891f21eac07e16ad4cab"
     */
    @Id
    @Column(name = "user_id", nullable = false, unique = true, length = 24)
    private String userId;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
}
