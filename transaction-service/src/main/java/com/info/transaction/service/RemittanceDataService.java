package com.info.transaction.service;

import com.info.dto.account.PaymentDTO;
import com.info.dto.constants.Constants;
import com.info.dto.remittance.RemittanceDataDTO;
import com.info.transaction.feignclient.InstantCashReplicaServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component("remittanceDataService")
public class RemittanceDataService implements JavaDelegate {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RemittanceDataService.class.getName());
    private final InstantCashReplicaServiceFeignClient instantCashReplicaServiceFeignClient;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String reference = (String) execution.getVariable("reference");
        logger.info("RemittanceDataService for reference number: {}", reference);

        kafkaTemplate.send(Constants.TOPIC_SMS, "Fetching RemittanceDataDTO for reference: " + reference);
        RemittanceDataDTO remittanceDataDTO = instantCashReplicaServiceFeignClient.findByReference(reference);
        String referenceValidationError = String.format("%s %s: %s", Constants.TRANSACTION_POSTING_FAILED, Constants.REFERENCE_NOT_EXIST, reference);
        logger.info("Transaction RemittanceDataDTO: {}", remittanceDataDTO);
        if (remittanceDataDTO == null) {
            execution.setVariable("validationStatus", "FAILED");
            execution.setVariable("errorMessage", referenceValidationError);
            throw new BpmnError("ERROR_VALIDATE_REFERENCE", referenceValidationError);
        }

        try {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setReference(reference);
            paymentDTO.setAmount(remittanceDataDTO.getAmount().doubleValue());
            paymentDTO.setCurrencyCode(remittanceDataDTO.getCurrencyCode());
            paymentDTO.setAccountNumber(remittanceDataDTO.getCreditorAccountNo());
            paymentDTO.setBranchCode(Integer.parseInt(remittanceDataDTO.getBranchRoutingNumber()));
            logger.info("Transaction PaymentDTO: {}", paymentDTO);

            // Set paymentDTO in the execution context
            execution.setVariable("paymentDTO", paymentDTO);
            execution.setVariable("accountNumber", remittanceDataDTO.getCreditorAccountNo());
        } catch (Exception e) {
            kafkaTemplate.send(Constants.TOPIC_SMS, "Error in RemittanceDataService: " + e.getMessage());
            logger.error("================Error in RemittanceDataService: {}", e.getMessage(), e);
            execution.setVariable("errorMessage", Constants.TRANSACTION_POSTING_FAILED);
            throw new BpmnError("ERROR_VALIDATE_REFERENCE", "Transaction Posting failed!");
        }
    }
}