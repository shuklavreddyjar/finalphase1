package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.ReportDocument;
import com.kirana.finalphase1.repository.mongo.ReportMongoRepository;
import org.springframework.stereotype.Service;

/**
 * The type Report query service.
 */
@Service
public class ReportQueryService {

    private final ReportMongoRepository reportRepository;

    /**
     * Instantiates a new Report query service.
     *
     * @param reportRepository the report repository
     */
    public ReportQueryService(ReportMongoRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Fetch report using BUSINESS ID (requestId)
     *
     * @param requestId the request id
     * @return the report by id
     */
    public ReportDocument getReportById(String requestId) {
        return reportRepository.findByRequestId(requestId)
                .orElseThrow(() ->
                        new RuntimeException("Report not found"));
    }
}
