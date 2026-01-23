package com.kirana.finalphase1.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * The type User document.
 */
@Data
@Document(collection = "users")
public class UserDocument {

    /**
     * MongoDB primary key.
     * This ObjectId will be used as userId across the entire system
     * (JWT, Postgres, Reports, Transactions).
     */
    @Id
    private ObjectId id;

    private String email;
    private String password;
    private String role;   // USER / ADMIN

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
