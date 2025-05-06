package com.info.transaction.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("transactionPostingService")
public class TransactionPostingService implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(TransactionPostingService.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String reference = (String) execution.getVariable("reference");
        logger.info("Transaction Posting for reference: {}", reference);

        // Simulate failure
        if (reference.equals("555")) {
            throw new BpmnError("ERROR_PROCESS_PAYMENT", "Transaction Posting failed!");
        }
    }
}

