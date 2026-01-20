package com.kirana.finalphase1.repository;

import com.kirana.finalphase1.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Date;
import java.util.List;

public interface TransactionRepository
        extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByUserIdAndCreatedAtBetween(
            String userId,
            Date from,
            Date to
    );
}
