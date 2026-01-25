package com.kirana.finalphase1.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "inventory")
public class InventoryDocument {

    @Id
    private ObjectId id;

    private String productId;     // Mongo productId
    private Integer quantityAvailable;
}
