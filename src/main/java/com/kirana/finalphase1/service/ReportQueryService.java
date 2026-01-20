package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.ReportDocument;
import com.kirana.finalphase1.repository.mongo.ReportMongoRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportQueryService {

    private final ReportMongoRepository reportRepository;

    public ReportQueryService(ReportMongoRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * Fetch report using BUSINESS ID (requestId)
     */
    public ReportDocument getReportById(String requestId) {
        return reportRepository.findByRequestId(requestId)
                .orElseThrow(() ->
                        new RuntimeException("Report not found"));
    }
}
