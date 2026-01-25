package com.kirana.finalphase1.repository.mongo;

import com.kirana.finalphase1.document.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductMongoRepository
        extends MongoRepository<ProductDocument, String> {

    List<ProductDocument> findByActiveTrue();
}
