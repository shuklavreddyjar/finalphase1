package com.kirana.finalphase1.repository.mongo;

import com.kirana.finalphase1.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {

    Optional<UserDocument> findByEmail(String email);
}
