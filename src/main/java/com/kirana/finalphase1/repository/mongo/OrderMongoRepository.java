package com.kirana.finalphase1.repository.mongo;

import com.kirana.finalphase1.document.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderMongoRepository
        extends MongoRepository<OrderDocument, String> {

    List<OrderDocument> findByUserId(String userId);
}
