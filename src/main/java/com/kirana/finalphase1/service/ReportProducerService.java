package com.kirana.finalphase1.service;

import com.kirana.finalphase1.dto.ReportRequestMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Report producer service.
 */
@Service
public class ReportProducerService {

    private static final String TOPIC = "report_requests";

    private final KafkaTemplate<String, ReportRequestMessage> kafkaTemplate;

    /**
     * Instantiates a new Report producer service.
     *
     * @param kafkaTemplate the kafka template
     */
    public ReportProducerService(KafkaTemplate<String, ReportRequestMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send report request.
     *
     * @param message the message
     */
    public void sendReportRequest(ReportRequestMessage message) {
        kafkaTemplate.send(TOPIC, message.getRequestId(), message);
    }
}
