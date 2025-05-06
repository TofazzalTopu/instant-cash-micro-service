package com.info.transaction.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cancelPaymentService")
public class CancelPaymentService implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(CancelPaymentService.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String reference = (String) execution.getVariable("reference");
        logger.info("Cancelling payment for reference: {}", reference);
    }
}

