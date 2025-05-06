package com.info.transaction.service;

import com.info.transaction.exception.InvalidRequestException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("paymentService")
public class PaymentService implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String orderId = (String) execution.getVariable("orderId");
        logger.info("Processing payment for Order ID: {}", orderId);

        // Simulate failure
        if (orderId.endsWith("5")) {
            throw new InvalidRequestException("Payment failed!");
        }
    }
}
