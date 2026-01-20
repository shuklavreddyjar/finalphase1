package com.kirana.finalphase1.repository.mongo;

import com.kirana.finalphase1.document.ReportDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReportMongoRepository
        extends MongoRepository<ReportDocument, ObjectId> {

    Optional<ReportDocument> findByRequestId(String requestId);
}
