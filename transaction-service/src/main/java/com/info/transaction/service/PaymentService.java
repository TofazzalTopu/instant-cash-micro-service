package com.info.transaction.service;

import com.info.dto.account.AccountBalanceDTO;
import com.info.dto.account.PaymentDTO;
import com.info.transaction.exception.InvalidRequestException;
import com.info.transaction.feignClient.AccountServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("paymentService")
@RequiredArgsConstructor
public class PaymentService implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class.getName());

    private final AccountServiceFeignClient accountServiceFeignClient;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String reference = (String) execution.getVariable("reference");
        String accountNumber = (String) execution.getVariable("accountNumber");
        logger.info("Transaction Posting for reference: {}", reference);
        logger.info("Processing payment for accountNumber: {}", accountNumber);

        PaymentDTO paymentDTO = (PaymentDTO) execution.getVariable("paymentDTO");
        String userId = (String) execution.getVariable("userId");
        String password = (String) execution.getVariable("password");

        if (reference == null || paymentDTO == null) {
            logger.error("Missing required process variables: reference={}, userId={}, paymentDTO={}", reference, userId, paymentDTO);
            throw new InvalidRequestException("Missing required process variables.");
        }

        try {
            AccountBalanceDTO accountBalanceDTO = accountServiceFeignClient.updateAccountBalance(paymentDTO);
            logger.info("Payment processed. Updated balance: {}", accountBalanceDTO);
            execution.setVariable("paymentStatus", "SUCCESS");
        } catch (Exception ex) {
            logger.error("Payment failed for reference {}: {}", reference, ex.getMessage(), ex);
            execution.setVariable("paymentStatus", "FAILED");
            throw ex;
        }
        // Simulate failure
//        if (orderId.endsWith("5")) {
//            throw new InvalidRequestException("Payment failed!");
//        }
    }
}
