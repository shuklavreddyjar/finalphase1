package com.kirana.finalphase1.repository;

import com.kirana.finalphase1.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findByUserId(String userId);
}
