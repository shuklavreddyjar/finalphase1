package com.kirana.finalphase1.document;

import com.kirana.finalphase1.enums.ReportStatus;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The type Report document.
 */
@Data
@Document(collection = "reports")
public class ReportDocument {

    /**
     * MongoDB primary key (_id)
     */
    @Id
    private ObjectId id;

    /**
     * Business identifier for report tracking
     */
    private String requestId;

    /**
     * MongoDB User _id stored as hex string
     * Example: "696a891f21eac07e16ad4cab"
     */
    private String userId;

    private Date fromTime;
    private Date toTime;

    private ReportStatus status;

    private BigDecimal totalCredit;
    private BigDecimal totalDebit;
    private BigDecimal netFlow;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;
}
