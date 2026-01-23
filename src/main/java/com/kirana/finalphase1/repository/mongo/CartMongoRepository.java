package com.kirana.finalphase1.repository.mongo;

import com.kirana.finalphase1.document.CartDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * The interface Cart mongo repository.
 */
public interface CartMongoRepository
        extends MongoRepository<CartDocument, String> {

    /**
     * Find by user id and status optional.
     *
     * @param userId the user id
     * @param status the status
     * @return the optional
     */
    Optional<CartDocument> findByUserIdAndStatus(
            String userId,
            CartDocument.CartStatus status
    );
}
