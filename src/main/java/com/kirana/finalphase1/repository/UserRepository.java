package com.kirana.finalphase1.repository;

import com.kirana.finalphase1.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends MongoRepository<UserDocument, String> {

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<UserDocument> findByEmail(String email);
}
