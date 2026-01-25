package com.kirana.finalphase1.repository.mongo;

import com.kirana.finalphase1.document.InventoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryMongoRepository
        extends MongoRepository<InventoryDocument, String> {

    Optional<InventoryDocument> findByProductId(String productId);
}
