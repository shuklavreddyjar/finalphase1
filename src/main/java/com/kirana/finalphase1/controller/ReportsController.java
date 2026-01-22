package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.document.ReportDocument;
import com.kirana.finalphase1.dto.ReportRequestDTO;
import com.kirana.finalphase1.dto.ReportRequestMessage;
import com.kirana.finalphase1.security.SecurityUtils;
import com.kirana.finalphase1.service.ReportProducerService;
import com.kirana.finalphase1.service.ReportQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    private final ReportProducerService reportProducerService;
    private final ReportQueryService reportQueryService;

    public ReportsController(
            ReportProducerService reportProducerService,
            ReportQueryService reportQueryService
    ) {
        this.reportProducerService = reportProducerService;
        this.reportQueryService = reportQueryService;
    }

    /**
     * ASYNC REPORT REQUEST
     */
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, String> requestReport(
            @RequestBody ReportRequestDTO request
    ) {

        String requestId = UUID.randomUUID().toString();

        // MongoDB userId (ObjectId hex string) from JWT
        String userId = SecurityUtils.getCurrentUserId();

        ReportRequestMessage message = new ReportRequestMessage();
        message.setRequestId(requestId);
        message.setUserId(userId);          // NOT email
        message.setFromTime(request.getFromTime());
        message.setToTime(request.getToTime());

        reportProducerService.sendReportRequest(message);

        return Map.of(
                "message", "Report request accepted",
                "requestId", requestId
        );
    }

    /**
     * FETCH REPORT BY REQUEST ID
     */
    @GetMapping("/{requestId}")
    public ReportDocument getReport(@PathVariable String requestId) {
        return reportQueryService.getReportById(requestId);
    }
}
