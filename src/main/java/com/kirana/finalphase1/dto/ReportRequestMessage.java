package com.kirana.finalphase1.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ReportRequestMessage {

    private String requestId;
    private String userId;
    private Date fromTime;
    private Date toTime;


}
