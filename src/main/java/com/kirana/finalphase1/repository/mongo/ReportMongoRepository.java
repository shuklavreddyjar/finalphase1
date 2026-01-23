package com.kirana.finalphase1.repository.mongo;

import com.kirana.finalphase1.document.ReportDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * The interface Report mongo repository.
 */
public interface ReportMongoRepository
        extends MongoRepository<ReportDocument, ObjectId> {

    /**
     * Find by request id optional.
     *
     * @param requestId the request id
     * @return the optional
     */
    Optional<ReportDocument> findByRequestId(String requestId);
}
