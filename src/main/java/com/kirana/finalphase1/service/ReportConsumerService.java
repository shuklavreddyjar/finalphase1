package com.kirana.finalphase1.service;

import com.kirana.finalphase1.document.ReportDocument;
import com.kirana.finalphase1.dto.ReportRequestMessage;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.enums.ReportStatus;
import com.kirana.finalphase1.enums.TransactionType;
import com.kirana.finalphase1.repository.TransactionRepository;
import com.kirana.finalphase1.repository.mongo.ReportMongoRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Kafka consumer that generates reports asynchronously.
 * Uses MongoDB userId (ObjectId hex string) end-to-end.
 */
@Service
public class ReportConsumerService {

    private final TransactionRepository transactionRepository;
    private final ReportMongoRepository reportMongoRepository;

    public ReportConsumerService(TransactionRepository transactionRepository,
                                 ReportMongoRepository reportMongoRepository) {
        this.transactionRepository = transactionRepository;
        this.reportMongoRepository = reportMongoRepository;
    }

    @KafkaListener(
            topics = "report_requests",
            groupId = "report-consumer-group"
    )
    public void consumeReportRequest(ReportRequestMessage message) {

        // Create report (timestamps handled automatically)
        ReportDocument report = new ReportDocument();
        report.setRequestId(message.getRequestId());
        report.setUserId(message.getUserId());
        report.setFromTime(message.getFromTime());
        report.setToTime(message.getToTime());
        report.setStatus(ReportStatus.IN_PROGRESS);

        try {
            // Fetch transactions using userId (NOT email)
            List<TransactionEntity> transactions =
                    transactionRepository.findByUserIdAndCreatedAtBetween(
                            message.getUserId(),
                            message.getFromTime(),
                            message.getToTime()
                    );

            BigDecimal totalCredit = BigDecimal.ZERO;
            BigDecimal totalDebit = BigDecimal.ZERO;

            for (TransactionEntity tx : transactions) {
                if (tx.getType() == TransactionType.CREDIT) {
                    totalCredit = totalCredit.add(tx.getAmount());
                } else if (tx.getType() == TransactionType.DEBIT) {
                    totalDebit = totalDebit.add(tx.getAmount());
                }
            }

            BigDecimal netFlow = totalCredit.subtract(totalDebit);

            report.setTotalCredit(totalCredit);
            report.setTotalDebit(totalDebit);
            report.setNetFlow(netFlow);
            report.setStatus(ReportStatus.COMPLETED);

        } catch (Exception e) {

            report.setTotalCredit(BigDecimal.ZERO);
            report.setTotalDebit(BigDecimal.ZERO);
            report.setNetFlow(BigDecimal.ZERO);
            report.setStatus(ReportStatus.FAILED);
        }

        // Save report (MongoDB ObjectId auto-generated)
        reportMongoRepository.save(report);
    }
}
