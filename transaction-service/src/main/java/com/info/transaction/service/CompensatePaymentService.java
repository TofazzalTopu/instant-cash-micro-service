package com.info.transaction.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("compensatePaymentService")
public class CompensatePaymentService implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(CompensatePaymentService.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String reference = (String) execution.getVariable("reference");
        // Call a reversal or refund API here
        logger.info("Compensating payment for reference: {}", reference);
    }
}
