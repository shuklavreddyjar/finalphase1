package com.kirana.finalphase1.repository;

import com.kirana.finalphase1.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Date;
import java.util.List;

/**
 * The interface Transaction repository.
 */
public interface TransactionRepository
        extends JpaRepository<TransactionEntity, UUID> {

    /**
     * Find by user id and created at between list.
     *
     * @param userId the user id
     * @param from   the from
     * @param to     the to
     * @return the list
     */
    List<TransactionEntity> findByUserIdAndCreatedAtBetween(
            String userId,
            Date from,
            Date to
    );
}
