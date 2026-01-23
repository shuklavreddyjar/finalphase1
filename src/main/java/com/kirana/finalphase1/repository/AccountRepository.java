package com.kirana.finalphase1.repository;

import com.kirana.finalphase1.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Account repository.
 */
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    /**
     * Find by user id optional.
     *
     * @param userId the user id
     * @return the optional
     */
    Optional<AccountEntity> findByUserId(String userId);
}
