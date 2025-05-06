package com.info.transaction.service;

import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;
import com.info.dto.kafka.EmailData;
import com.info.transaction.feignclient.AccountServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("accountService")
@RequiredArgsConstructor
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class.getName());
    @Autowired
    private KafkaTemplate<String, EmailData> kafkaTemplate;
    private final AccountServiceFeignClient accountServiceFeignClient;

    public AccountBalanceDTO updateAccountBalance(String reference, String userId, PaymentDTO paymentDTO, DelegateExecution execution) {

        if (reference == null || paymentDTO == null) {
            logger.error("================ERROR_FINALIZE_PAYMENT Missing required process variables: reference={}, userId={}, paymentDTO={}", reference, userId, paymentDTO);
            throw new BpmnError("ERROR_FINALIZE_PAYMENT", "Missing required process variables.");
        }

        try {
            AccountBalanceDTO accountBalanceDTO = accountServiceFeignClient.updateAccountBalance(paymentDTO);
            logger.info("=======Payment processed successfully. Updated balance: {}", accountBalanceDTO);
            execution.setVariable("paymentStatus", "SUCCESS");
            return accountBalanceDTO;
        } catch (Exception ex) {
            logger.error("====== ERROR_FINALIZE_PAYMENT Payment failed for reference {}: {}", reference, ex.getMessage());
            execution.setVariable("paymentStatus", "FAILED");
            throw new BpmnError("ERROR_FINALIZE_PAYMENT", "Account Balance Update Failed!");
        }
    }
}
