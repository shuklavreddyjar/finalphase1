package com.kirana.finalphase1.dto;
import com.kirana.finalphase1.enums.ReportStatus;
import lombok.Data;

/**
 * The type Report response dto.
 */
@Data
public class ReportResponseDTO {

    private String requestId;
    private ReportStatus status;


}
