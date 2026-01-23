package com.kirana.finalphase1.mapper;

import com.kirana.finalphase1.dto.TransactionResponseDTO;
import com.kirana.finalphase1.entity.TransactionEntity;
import com.kirana.finalphase1.enums.CurrencyType;
import com.kirana.finalphase1.enums.TransactionStatus;
import org.springframework.stereotype.Service;

/**
 * The type Transaction response mapper.
 */
@Service
public class TransactionResponseMapper {

    /**
     * Success transaction response dto.
     *
     * @param tx the tx
     * @return the transaction response dto
     */
    public TransactionResponseDTO success(TransactionEntity tx) {

        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setTransactionId(tx.getTransactionId());
        response.setUserId(tx.getUserId());
        response.setAmount(tx.getAmount());
        response.setCurrency(CurrencyType.INR);
        response.setType(tx.getType().name());
        response.setStatus(TransactionStatus.SUCCESS);
        response.setCreatedAt(tx.getCreatedAt());

        return response;
    }
}
