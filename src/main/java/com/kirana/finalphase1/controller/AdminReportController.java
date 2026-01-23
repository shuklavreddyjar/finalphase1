package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.document.ReportDocument;
import com.kirana.finalphase1.service.ReportQueryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The type Admin report controller.
 */
@RestController
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final ReportQueryService reportQueryService;

    /**
     * Instantiates a new Admin report controller.
     *
     * @param reportQueryService the report query service
     */
    public AdminReportController(ReportQueryService reportQueryService) {
        this.reportQueryService = reportQueryService;
    }

    /**
     * Gets report.
     *
     * @param requestId the request id
     * @return the report
     */
// ADMIN can fetch ANY report
    @GetMapping("/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ReportDocument getReport(@PathVariable String requestId) {
        return reportQueryService.getReportById(requestId);
    }
}
