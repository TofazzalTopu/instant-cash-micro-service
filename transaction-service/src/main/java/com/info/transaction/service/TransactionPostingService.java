package com.info.transaction.service;

import com.info.transaction.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("transactionPostingService")
public class TransactionPostingService implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(TransactionPostingService.class.getName());

    private final RemittanceDataService remittanceDataService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String reference = (String) execution.getVariable("reference");
        logger.info("Transaction Posting for the Rem ID: {}", reference);

        // Simulate failure
        if (reference.equals("555")) {
            throw new InvalidRequestException("Transaction Posting failed!");
        }
    }
}

