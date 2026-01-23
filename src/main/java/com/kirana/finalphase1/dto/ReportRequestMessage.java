package com.kirana.finalphase1.dto;

import lombok.Data;

import java.util.Date;

/**
 * The type Report request message.
 */
@Data
public class ReportRequestMessage {

    private String requestId;
    private String userId;
    private Date fromTime;
    private Date toTime;


}
