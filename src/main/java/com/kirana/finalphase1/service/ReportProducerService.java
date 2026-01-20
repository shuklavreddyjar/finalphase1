package com.kirana.finalphase1.service;

import com.kirana.finalphase1.dto.ReportRequestMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReportProducerService {

    private static final String TOPIC = "report_requests";

    private final KafkaTemplate<String, ReportRequestMessage> kafkaTemplate;

    public ReportProducerService(KafkaTemplate<String, ReportRequestMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReportRequest(ReportRequestMessage message) {
        kafkaTemplate.send(TOPIC, message.getRequestId(), message);
    }
}
