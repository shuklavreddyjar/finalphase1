package com.kirana.finalphase1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * The type Report request dto.
 */
@Data
public class ReportRequestDTO {

    @JsonProperty("from_time")
    private Date fromTime;

    @JsonProperty("to_time")
    private Date toTime;
}
